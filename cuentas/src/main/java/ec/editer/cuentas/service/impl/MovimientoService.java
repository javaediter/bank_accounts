/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.service.impl;

import ec.editer.commons.cuentas.dtos.CuentaDTO;
import ec.editer.commons.cuentas.dtos.MovimientoDTO;
import ec.editer.cuentas.model.Cuenta;
import ec.editer.cuentas.model.Movimiento;
import ec.editer.cuentas.model.TipoMovimiento;
import ec.editer.cuentas.repository.MovimientoRepository;
import ec.editer.cuentas.service.IMovimientoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Service
public class MovimientoService implements IMovimientoService{
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<MovimientoDTO> registrarMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = new Movimiento();
        BeanUtils.copyProperties(movimientoDTO, movimiento);
        
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(movimientoDTO.getCuenta().getCuentaId());
        
        TipoMovimiento tipoMovimiento = new TipoMovimiento();
        tipoMovimiento.setTipoMovimientoId(movimientoDTO.getTipoMovimiento().getTipoMovimientoId());
        
        movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento(tipoMovimiento);
        
        movimientoRepository.save(movimiento);
        
        movimientoDTO.setMovimientoId(movimiento.getMovimientoId());        
        return Optional.of(movimientoDTO);
    }

    @Override
    public List<MovimientoDTO> obtenerMovimientosPorCliente(Integer clienteId, Date fechaInicio, Date fechaFin) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        java.sql.Date fechaStart = new java.sql.Date(fechaInicio.getTime());
        java.sql.Date fechaEnd = new java.sql.Date(fechaFin.getTime());
        movimientoRepository.findAllByCuentaClienteIdAndFechaBetween(clienteId, fechaStart, fechaEnd).forEach(x -> {
            MovimientoDTO movimientoDTO = new MovimientoDTO();
            BeanUtils.copyProperties(x, movimientoDTO);
            movimientoDTO.setCuenta(new CuentaDTO());
            BeanUtils.copyProperties(x.getCuenta(), movimientoDTO.getCuenta());
            movimientoDTO.getCuenta().setNombreCliente(obtenerNombreCliente(clienteId));
            movimientosDTO.add(movimientoDTO);
        });
        return movimientosDTO;
    }
    
    private String obtenerNombreCliente(Integer clienteId){
        StringBuilder sql = new StringBuilder("SELECT c.nombre AS cliente ");
        sql.append("FROM cuentas_bancarias.cuentas q ");
        sql.append("INNER JOIN cuentas_bancarias.clientes c ON q.cliente_id = c.cliente_id ");
        sql.append("WHERE c.cliente_id = 1;");
        return jdbcTemplate.queryForObject(sql.toString(), String.class);
    }
    
}
