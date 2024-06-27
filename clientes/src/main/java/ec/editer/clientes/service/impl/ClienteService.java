/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.service.impl;

import ec.editer.clientes.model.Cliente;
import ec.editer.clientes.model.EstadoCliente;
import ec.editer.clientes.repository.ClienteRepository;
import ec.editer.clientes.repository.EstadoClienteRepository;
import ec.editer.clientes.service.IClienteService;
import ec.editer.clientes.utils.EncriptadorBasico;
import ec.editer.commons.clientes.dtos.ClienteDTO;
import ec.editer.commons.clientes.dtos.ClienteDataParcial;
import ec.editer.commons.clientes.dtos.EstadoClienteDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Service
public class ClienteService implements IClienteService {
    
    @Autowired
    private EncriptadorBasico encriptadorBasico;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstadoClienteRepository estadoClienteRepository;

    @Override
    public List<ClienteDTO> obtenerClientes() {
        List<ClienteDTO> clientes = new ArrayList<>();
        clienteRepository.findAll().iterator().forEachRemaining(x -> {
            EstadoClienteDTO estadoClienteDTO = EstadoClienteDTO.builder()
                    .estadoClienteId(x.getEstadoCliente().getEstadoClienteId())
                    .valor(x.getEstadoCliente().isValor())
                    .build();
            
            ClienteDTO clienteDTO = ClienteDTO.builder()
                    .clienteId(x.getClienteId())
                    .direccion(x.getDireccion())
                    .edad(x.getEdad())
                    .genero(x.getGenero())
                    .identificacion(x.getIdentificacion())
                    .nombre(x.getNombre())
                    .password(x.getPassword())
                    .telefono(x.getTelefono())
                    .estadoCliente(estadoClienteDTO)
                    .build();
            clientes.add(clienteDTO);
        });
        return clientes;
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) throws DataIntegrityViolationException {
        clienteDTO.setPassword(encriptadorBasico.encriptador(clienteDTO.getPassword()));
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        cliente.setEstadoCliente(new EstadoCliente());
        cliente.getEstadoCliente().setEstadoClienteId(clienteDTO.getEstadoCliente().getEstadoClienteId());

        cliente = clienteRepository.save(cliente);
        clienteDTO.setClienteId(cliente.getClienteId());
        return clienteDTO;
    }

    @Override
    public Optional<ClienteDTO> obtenerClientePorId(Integer clienteId) {
        Optional<Cliente> opt = clienteRepository.findById(clienteId);
        
        if(opt.isPresent()){
            Cliente x = opt.get();
            EstadoClienteDTO estadoClienteDTO = EstadoClienteDTO.builder()
                    .estadoClienteId(x.getEstadoCliente().getEstadoClienteId())
                    .valor(x.getEstadoCliente().isValor())
                    .build();
            
            ClienteDTO clienteDTO = ClienteDTO.builder()
                    .clienteId(x.getClienteId())
                    .direccion(x.getDireccion())
                    .edad(x.getEdad())
                    .genero(x.getGenero())
                    .identificacion(x.getIdentificacion())
                    .nombre(x.getNombre())
                    .password(x.getPassword())
                    .telefono(x.getTelefono())
                    .estadoCliente(estadoClienteDTO)
                    .build();
            
            return Optional.of(clienteDTO);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void eliminarCliente(Integer clienteId) {
        clienteRepository.deleteById(clienteId);
    }

    @Override
    public Optional<ClienteDTO> actualizacionDataParcial(ClienteDataParcial clienteDataParcial) {        
        Optional<Cliente> cliente = clienteRepository.findById(clienteDataParcial.getClienteId());
        if (clienteDataParcial.getDireccion() == null || clienteDataParcial.getDireccion().isEmpty()) {
            clienteDataParcial.setDireccion(cliente.get().getNombre());
        }
        if (clienteDataParcial.getTelefono() == null || clienteDataParcial.getTelefono().isEmpty()) {
            clienteDataParcial.setTelefono(cliente.get().getTelefono());
        }
        if (clienteDataParcial.getEdad() == null || clienteDataParcial.getEdad() == 0) {
            clienteDataParcial.setEdad(cliente.get().getEdad());
        }
        if(clienteDataParcial.getNuevaPassword()==null || clienteDataParcial.getNuevaPassword().isEmpty()){
            clienteDataParcial.setNuevaPassword(cliente.get().getPassword());
        }else{
            clienteDataParcial.setNuevaPassword(encriptadorBasico.encriptador(clienteDataParcial.getNuevaPassword()));
        }
        
        cliente.ifPresent(x -> {
            x.setEstadoCliente(x.getEstadoCliente());
            x.setDireccion(clienteDataParcial.getDireccion());
            x.setTelefono(clienteDataParcial.getTelefono());
            x.setEdad(clienteDataParcial.getEdad());
            x.setPassword(clienteDataParcial.getNuevaPassword());
            
            Optional<EstadoCliente> optEstadoCliente = estadoClienteRepository.findById(clienteDataParcial.getEstadoId());
            
            optEstadoCliente.ifPresent(ec -> {
                x.setEstadoCliente(ec);
            });
        });
        
        clienteRepository.save(cliente.get());
        
        Cliente x = cliente.get();
            EstadoClienteDTO estadoClienteDTO = EstadoClienteDTO.builder()
                    .estadoClienteId(x.getEstadoCliente().getEstadoClienteId())
                    .valor(x.getEstadoCliente().isValor())
                    .build();
            
            ClienteDTO clienteDTO = ClienteDTO.builder()
                    .clienteId(x.getClienteId())
                    .direccion(x.getDireccion())
                    .edad(x.getEdad())
                    .genero(x.getGenero())
                    .identificacion(x.getIdentificacion())
                    .nombre(x.getNombre())
                    .password(x.getPassword())
                    .telefono(x.getTelefono())
                    .estadoCliente(estadoClienteDTO)
                    .build();
            
        return Optional.of(clienteDTO);
    }
}
