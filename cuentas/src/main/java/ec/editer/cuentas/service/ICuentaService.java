/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.service;

import ec.editer.cuentas.dtos.CuentaDTO;
import ec.editer.cuentas.dtos.CuentaDataParcial;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Edison Teran
 */
public interface ICuentaService {
    public Optional<CuentaDTO> registrarCuenta(CuentaDTO cuentaDTO) throws DataIntegrityViolationException;
    public List<CuentaDTO> obtenerCuentasPorCliente(Integer clienteId);
    public List<CuentaDTO> obtenerCuentasPorClienteYPorTipoCuenta(Integer clienteId, Integer tipoCuentaId);
    public Optional<CuentaDTO> actualizarCuenta(CuentaDataParcial cuentaDataParcial);
    public BigDecimal actualizarSaldoDisponibleCuenta(BigDecimal nuevoSaldo, Integer cuentaId);
    public BigDecimal obtenerSaldoDisponibleCuenta(Integer cuentaId);
}
