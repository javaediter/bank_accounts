/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.repository;

import ec.editer.cuentas.model.Movimiento;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface MovimientoRepository extends CrudRepository<Movimiento, Integer>{
    public List<Movimiento> findAllByCuentaClienteIdAndFechaBetween(Integer clienteId, Date fechaStart, Date fechaEnd);
}
