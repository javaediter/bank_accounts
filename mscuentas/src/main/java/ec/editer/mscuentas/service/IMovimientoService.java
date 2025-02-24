/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service;

import ec.editer.mscuentas.dtos.MovimientoDTO;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface IMovimientoService {
    MovimientoDTO registrarMovimiento(MovimientoDTO movimientoDTO);
    Optional<MovimientoDTO> obtenerMovimientoPorMovimientoId(Integer movimientoId);
    List<MovimientoDTO> obtenerMovimientosPorCliente(Integer clienteId, Date fechaInicio, Date fechaFin);
    List<MovimientoDTO> obtenerMovimientosPorCuenta(Integer cuentaId, String tipoOrdenacion);
    void eliminarMovimiento(Integer movimientoId);
}
