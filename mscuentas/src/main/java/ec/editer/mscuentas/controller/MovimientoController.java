/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.controller;

import ec.editer.mscuentas.dtos.CuentaDTO;
import ec.editer.mscuentas.dtos.MovimientoDTO;
import ec.editer.mscuentas.reporte.Registro;
import ec.editer.mscuentas.reporte.Reporte;
import ec.editer.mscuentas.service.ICuentaService;
import ec.editer.mscuentas.service.IMovimientoService;
import ec.editer.mscuentas.service.IReporteSevice;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ec.editer.mscuentas.service.amqp.IReportePublisher;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "*/*")
public class MovimientoController {

    @Autowired
    private ICuentaService cuentaService;

    @Autowired
    private IMovimientoService movimientoService;

    @Autowired
    private IReporteSevice reporteService;
    
    @Autowired
    private IReportePublisher reportePublisher;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        log.info("registrarMovimiento");

        //En caso de que el valor sea 0
        if (movimientoDTO.getValor().doubleValue() == 0) {
            return new ResponseEntity<>("Valor incorrecto", HttpStatus.NOT_ACCEPTABLE);
        }

        BigDecimal saldo = new BigDecimal(0);
        BigDecimal saldoDisponible = new BigDecimal(0);
        //Se consulta si tiene movimientos
        List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosPorCuenta(movimientoDTO.getCuenta().getCuentaId(), "DESC");

        if (movimientos.isEmpty()) {
            //Se obtiene el saldo inicial de la cuenta
            CuentaDTO cuentaDTO = cuentaService.obtenerCuentaPorCuentaId(movimientoDTO.getCuenta().getCuentaId());
            //Saldo inicial de la cuenta
            saldoDisponible = saldoDisponible.add(cuentaDTO.getSaldoInicial());
        } else {
            //Se toma el saldo del primer movimiento
            MovimientoDTO ultimo = movimientos.get(0);
            //Saldo ultimo movmiento
            saldoDisponible = saldoDisponible.add(ultimo.getSaldo());
        }

        if (movimientoDTO.getTipoMovimiento().toUpperCase().equals("RETIRO")) {
            //En caso de que el valor sea positivo se convierte en negativo
            if (movimientoDTO.getValor().doubleValue() > 0) {
                movimientoDTO.setValor(movimientoDTO.getValor().multiply(new BigDecimal(-1)));
            }
            if (movimientoDTO.getValor().doubleValue() * -1 > saldoDisponible.doubleValue()) {
                return new ResponseEntity<>("Saldo no disponible", HttpStatus.ACCEPTED);
            }
        } else {
            //En caso de que el valor sea negativo se convierte en positivo
            if (movimientoDTO.getValor().doubleValue() < 0) {
                movimientoDTO.setValor(movimientoDTO.getValor().multiply(new BigDecimal(-1)));
            }
        }
        saldo = saldo.add(saldoDisponible).add(movimientoDTO.getValor());
        movimientoDTO.setSaldo(saldo);
        movimientoService.registrarMovimiento(movimientoDTO);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> obtenerMovimientosPorCliente(@RequestParam("cliente_id") Integer clienteId, @RequestParam("fecha_inicio") String fechaInicio, @RequestParam("fecha_fin") String fechaFin) throws ParseException {
        log.info("obtenerMovimientosPorCliente");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaStart = dateFormat.parse(fechaInicio);
        Date fechaEnd = dateFormat.parse(fechaFin);
        return new ResponseEntity<>(movimientoService.obtenerMovimientosPorCliente(clienteId, new Date(fechaStart.getTime()), new Date(fechaEnd.getTime())), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarMovimiento(@RequestParam("movimiento_id") Integer movimientoId) {
        log.info("eliminarMovimiento");
        try {
            movimientoService.eliminarMovimiento(movimientoId);
            return new ResponseEntity<>("Registro eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<?> generarReporte(@RequestParam("cliente_id") Integer clienteId, @RequestParam("fecha_inicio") String fechaInicio, @RequestParam("fecha_fin") String fechaFin) {
        log.info("generarReporte");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {            
            Date fechaStart = dateFormat.parse(fechaInicio);
            Date fechaEnd = dateFormat.parse(fechaFin);
            java.sql.Date fechaStartSQL = new java.sql.Date(fechaStart.getTime());
            java.sql.Date fechaEndSQL = new java.sql.Date(fechaEnd.getTime());            
            
            List<Registro> registros = reporteService.construirReporte(fechaStartSQL, fechaEndSQL, clienteId);
            Reporte reporte = new Reporte(clienteId, registros);
            reportePublisher.publicarReporte(reporte);
            return new ResponseEntity<>(reporte, HttpStatus.OK);
        } catch (ParseException ex) {
            return new ResponseEntity<>("Formato incorrecto de fechas", HttpStatus.BAD_REQUEST);
        }
    }
}
