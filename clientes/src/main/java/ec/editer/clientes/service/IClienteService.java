/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.service;

import ec.editer.clientes.dtos.ClienteDTO;
import ec.editer.clientes.dtos.ClienteDataParcial;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Edison Teran
 */
public interface IClienteService {
    public List<ClienteDTO> obtenerClientes();
    public Optional<ClienteDTO> obtenerClientePorId(Integer clienteId);
    public ClienteDTO registrarCliente(ClienteDTO clienteDTO)throws DataIntegrityViolationException;
    public void eliminarCliente(Integer clienteId);
    public Optional<ClienteDTO> actualizacionDataParcial(ClienteDataParcial clienteDataParcial);
}
