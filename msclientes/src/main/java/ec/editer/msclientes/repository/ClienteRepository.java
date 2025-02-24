/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.repository;

import ec.editer.msclientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Edison Teran
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
    @Query("SELECT c.nombre FROM Cliente c WHERE c.clienteId = :id")
    String obtenerNombreCliente(@Param("id") Integer clienteId);
}
