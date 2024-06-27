/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.service;

import ec.editer.commons.cuentas.dtos.MovimientoDTO;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface IMovimientoService {
    public Optional<MovimientoDTO> registrarMovimiento(MovimientoDTO movimientoDTO);
    public List<MovimientoDTO> obtenerMovimientosPorCliente(Integer clienteId, Date fechaInicio, Date fechaFin);
}
