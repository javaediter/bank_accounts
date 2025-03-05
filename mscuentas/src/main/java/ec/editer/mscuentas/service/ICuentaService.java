/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service;

import ec.editer.mscuentas.dtos.CuentaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Edison Teran
 */
public interface ICuentaService {
    List<CuentaDTO> cuentasPorCliente(Integer clienteId);
    CuentaDTO registrarCuenta(CuentaDTO cuentaDTO) throws DataIntegrityViolationException;
    CuentaDTO actualizarCuenta(CuentaDTO cuentaDTO) throws DataIntegrityViolationException;
    CuentaDTO obtenerCuentaPorCuentaId(Integer cuentaId);
    Optional<CuentaDTO> obtenerCuentaPorNumeroTipo(String numero, String tipoCuenta);
    void eliminarCuenta(Integer cuentaId) throws Exception;
    List<Integer> obtenerClientesIds();
}
