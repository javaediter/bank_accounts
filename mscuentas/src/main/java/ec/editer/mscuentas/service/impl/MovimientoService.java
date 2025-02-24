/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service.impl;

import ec.editer.mscuentas.dtos.CuentaDTO;
import ec.editer.mscuentas.dtos.MovimientoDTO;
import ec.editer.mscuentas.model.Cuenta;
import ec.editer.mscuentas.model.Movimiento;
import ec.editer.mscuentas.repository.MovimientoRepository;
import ec.editer.mscuentas.service.ICuentaService;
import ec.editer.mscuentas.service.IMovimientoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Edison Teran
 */
@Service
public class MovimientoService implements IMovimientoService {

    @Value("${api.clientes}")
    private String apiClientes;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ICuentaService cuentaService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public MovimientoDTO registrarMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = new Movimiento();
        BeanUtils.copyProperties(movimientoDTO, movimiento);
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(movimientoDTO.getCuenta().getCuentaId());
        movimiento.setCuenta(cuenta);
        movimiento.setMovimientoId(null);
        movimientoRepository.save(movimiento);
        movimientoDTO.setMovimientoId(movimiento.getMovimientoId());
        return movimientoDTO;
    }

    @Override
    public Optional<MovimientoDTO> obtenerMovimientoPorMovimientoId(Integer movimientoId) {

        Optional<Movimiento> opt = movimientoRepository.findById(movimientoId);
        if (opt.isPresent()) {
            MovimientoDTO dto = new MovimientoDTO();
            BeanUtils.copyProperties(opt.get(), dto);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MovimientoDTO> obtenerMovimientosPorCliente(Integer clienteId, Date fechaInicio, Date fechaFin) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        java.sql.Date fechaStart = new java.sql.Date(fechaInicio.getTime());
        java.sql.Date fechaEnd = new java.sql.Date(fechaFin.getTime());
        String nombreCliente = restTemplate.getForObject(apiClientes + "/obtenerNombreCliente?cliente_id=" + clienteId, String.class);
        movimientoRepository.findAllByCuentaClienteIdAndFechaBetween(clienteId, fechaStart, fechaEnd).forEach(x -> {
            MovimientoDTO movimientoDTO = new MovimientoDTO();
            BeanUtils.copyProperties(x, movimientoDTO);
            movimientoDTO.setCuenta(new CuentaDTO());
            BeanUtils.copyProperties(x.getCuenta(), movimientoDTO.getCuenta());
            movimientoDTO.getCuenta().setNombreCliente(nombreCliente);
            movimientosDTO.add(movimientoDTO);
        });
        return movimientosDTO;
    }

    @Override
    public List<MovimientoDTO> obtenerMovimientosPorCuenta(Integer cuentaId, String tipoOrdenacion) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        CuentaDTO cuentaDTO = cuentaService.obtenerCuentaPorCuentaId(cuentaId);
        String nombreCliente = restTemplate.getForObject(apiClientes + "/obtenerNombreCliente?cliente_id=" + cuentaDTO.getClienteId(), String.class);
        movimientoRepository.findAllByCuentaCuentaId(cuentaId, Sort.by(tipoOrdenacion.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "movimientoId")).forEach(x -> {
            MovimientoDTO movimientoDTO = new MovimientoDTO();
            BeanUtils.copyProperties(x, movimientoDTO);
            movimientoDTO.setCuenta(new CuentaDTO());
            BeanUtils.copyProperties(x.getCuenta(), movimientoDTO.getCuenta());
            movimientoDTO.getCuenta().setNombreCliente(nombreCliente);
            movimientosDTO.add(movimientoDTO);
        });
        return movimientosDTO;
    }

    @Override
    public void eliminarMovimiento(Integer movimientoId) {
        movimientoRepository.deleteById(movimientoId);
    }

}
