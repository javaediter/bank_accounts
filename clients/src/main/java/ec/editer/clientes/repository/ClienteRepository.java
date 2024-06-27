/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.repository;

import ec.editer.clientes.model.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
    
}
