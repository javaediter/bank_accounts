/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.service.impl;

import ec.editer.msclientes.dtos.ClienteDTO;
import ec.editer.msclientes.enums.GeneroEnum;
import ec.editer.msclientes.model.Cliente;
import ec.editer.msclientes.repository.ClienteRepository;
import ec.editer.msclientes.service.IClienteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<ClienteDTO> obtenerTodos() {
        List<ClienteDTO> clientes = new ArrayList<>();
        clienteRepository.findAll().iterator().forEachRemaining(x -> {
            ClienteDTO dto = ClienteDTO.builder()
                    .clienteId(x.getClienteId())
                    .direccion(x.getDireccion())
                    .edad(x.getEdad())
                    .genero(x.getGenderEnum().name())
                    .identificacion(x.getIdentificacion())
                    .nombre(x.getNombre())
                    .contrasenia(x.getContrasenia())
                    .telefono(x.getTelefono())
                    .estado(x.isEstado())
                    .build();
            clientes.add(dto);
        });
        return clientes;
    }

    @Override
    public Optional<ClienteDTO> obtenerClientePorId(Integer clienteId) {
        Optional<Cliente> opt = clienteRepository.findById(clienteId);

        if (opt.isPresent()) {
            Cliente x = opt.get();

            ClienteDTO clienteDTO = ClienteDTO.builder()
                    .clienteId(x.getClienteId())
                    .direccion(x.getDireccion())
                    .edad(x.getEdad())
                    .genero(x.getGenderEnum().name())
                    .identificacion(x.getIdentificacion())
                    .nombre(x.getNombre())
                    .contrasenia(x.getContrasenia())
                    .telefono(x.getTelefono())
                    .estado(x.isEstado())
                    .build();

            return Optional.of(clienteDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) throws DataIntegrityViolationException {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        cliente.setGenderEnum(GeneroEnum.valueOf(clienteDTO.getGenero().toUpperCase()));
        cliente.setClienteId(null);
        cliente = clienteRepository.save(cliente);
        clienteDTO.setClienteId(cliente.getClienteId());
        return clienteDTO;
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO clienteDTO) throws DataIntegrityViolationException {
        Optional<Cliente> opt = clienteRepository.findById(clienteDTO.getClienteId());
        if (opt.isPresent()) {
            Cliente cliente = opt.get();
            cliente.setContrasenia(clienteDTO.getContrasenia());
            cliente.setDireccion(clienteDTO.getDireccion());
            cliente.setEdad(clienteDTO.getEdad());
            cliente.setEstado(clienteDTO.isEstado());
            cliente.setTelefono(clienteDTO.getTelefono());
            cliente.setGenderEnum(GeneroEnum.valueOf(clienteDTO.getGenero().toUpperCase()));
            clienteRepository.save(cliente);
            ClienteDTO dto = new ClienteDTO();
            BeanUtils.copyProperties(cliente, dto);
            return dto;
        } else {
            return registrarCliente(clienteDTO);
        }
    }

    @Override
    public void eliminarCliente(Integer clienteId) throws Exception {
        clienteRepository.deleteById(clienteId);
    }

    @Override
    public String obtenerNombreCliente(Integer clienteId) {
        return clienteRepository.obtenerNombreCliente(clienteId);
    }

}
