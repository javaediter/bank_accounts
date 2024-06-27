/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.controller;

import ec.editer.commons.cuentas.dtos.MovimientoDTO;
import ec.editer.cuentas.service.ICuentaService;
import ec.editer.cuentas.service.IMovimientoService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "*/*")
public class MovimientoController {

    private final static Integer TIPO_RETIRO = 2;

    @Autowired
    private ICuentaService cuentaService;

    @Autowired
    private IMovimientoService movimientoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        log.info("registrarMovimiento");
        BigDecimal saldoDisponibleCuenta = cuentaService.obtenerSaldoDisponibleCuenta(movimientoDTO.getCuenta().getCuentaId());
        log.info(String.format("saldo disponible %f para la cuenta %d", saldoDisponibleCuenta, movimientoDTO.getCuenta().getCuentaId()));

        movimientoDTO.setSaldoInicial(saldoDisponibleCuenta);

        if (movimientoDTO.getTipoMovimiento().getTipoMovimientoId().equals(TIPO_RETIRO)) {
            if (movimientoDTO.getValor().doubleValue() > 0) {
                movimientoDTO.setValor(movimientoDTO.getValor().multiply(new BigDecimal(-1)));
            }

            //se valida si tiene saldo suficiente para el retiro
            if (saldoDisponibleCuenta != null && saldoDisponibleCuenta.doubleValue() < Math.abs(movimientoDTO.getValor().doubleValue())) {
                return new ResponseEntity<>("Saldo no disponible", HttpStatus.ACCEPTED);
            }
        }

        movimientoDTO.setSaldoDisponible(movimientoDTO.getSaldoInicial().add(movimientoDTO.getValor()));
        movimientoService.registrarMovimiento(movimientoDTO);

        //Se actualiza el saldo disponible de la cuenta
        saldoDisponibleCuenta = movimientoDTO.getSaldoDisponible();
        cuentaService.actualizarSaldoDisponibleCuenta(saldoDisponibleCuenta, movimientoDTO.getCuenta().getCuentaId());

        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPorCliente(@RequestParam("cliente_id") Integer clienteId, @RequestParam("fecha_inicio") String fechaInicio, @RequestParam("fecha_fin") String fechaFin) throws ParseException {
        log.info("obtenerMovimientosPorCliente");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaStart = dateFormat.parse(fechaInicio);
        Date fechaEnd = dateFormat.parse(fechaFin);
        return new ResponseEntity<>(movimientoService.obtenerMovimientosPorCliente(clienteId, new Date(fechaStart.getTime()), new Date(fechaEnd.getTime())), HttpStatus.OK);
    }
}
