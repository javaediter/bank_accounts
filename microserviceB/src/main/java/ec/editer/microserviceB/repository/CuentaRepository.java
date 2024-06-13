/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.repository;

import ec.editer.microserviceB.model.Cuenta;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface CuentaRepository extends CrudRepository<Cuenta, Integer>{
    public List<Cuenta> findAllByClienteId(Integer clienteId);
    public List<Cuenta> findAllByClienteIdAndTipoCuentaTipoCuentaId(Integer clienteId, Integer tipoCuentaId);
    public Optional<Cuenta> findByNumeroAndTipoCuentaTipoCuentaId(String numero, Integer tipoCuentaId);
    
    @Query("SELECT Q.saldoDisponible FROM Cuenta Q WHERE Q.cuentaId = ?1")
    public BigDecimal findSaldoDisponibleByCuentaId(Integer cuentaId);
}
