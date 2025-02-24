/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.repository;

import ec.editer.mscuentas.model.Cuenta;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Edison Teran
 */
public interface CuentaRepository extends CrudRepository<Cuenta, Integer>{
    List<Cuenta> findAllByClienteId(Integer clienteId);
    Optional<Cuenta> findByNumeroAndTipoCuenta(String numero, String tipoCuenta);
    
    @Query("SELECT q.saldoInicial FROM Cuenta q WHERE q.cuentaId = :id")
    BigDecimal obtenerSaldoDelUltimoMovimiento(@Param("id") Integer cuentaId);
}
