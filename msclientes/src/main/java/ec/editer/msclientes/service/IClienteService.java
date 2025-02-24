/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.service;

import ec.editer.msclientes.dtos.ClienteDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Edison Teran
 */
public interface IClienteService {
    List<ClienteDTO> obtenerTodos();
    Optional<ClienteDTO> obtenerClientePorId(Integer clienteId);
    ClienteDTO registrarCliente(ClienteDTO clienteDTO)throws DataIntegrityViolationException;
    ClienteDTO actualizarCliente(ClienteDTO clienteDTO)throws DataIntegrityViolationException;
    void eliminarCliente(Integer clienteId) throws Exception;
    String obtenerNombreCliente(Integer clienteId);
}
