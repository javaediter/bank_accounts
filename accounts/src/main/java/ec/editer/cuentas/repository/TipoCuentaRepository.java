/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.repository;

import ec.editer.cuentas.model.TipoCuenta;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface TipoCuentaRepository extends CrudRepository<TipoCuenta, Integer>{
    
}
