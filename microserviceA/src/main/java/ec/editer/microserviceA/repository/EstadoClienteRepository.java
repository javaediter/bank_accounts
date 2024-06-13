/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceA.repository;

import ec.editer.microserviceA.model.EstadoCliente;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface EstadoClienteRepository extends CrudRepository<EstadoCliente, Integer>{
    
}
