package ec.editer.msclientes;

import ec.editer.msclientes.dtos.ClienteDTO;
import ec.editer.msclientes.model.Cliente;
import ec.editer.msclientes.repository.ClienteRepository;
import ec.editer.msclientes.service.impl.ClienteService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.BeanUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edison Teran
 */
@ExtendWith(MockitoExtension.class)
public class ClientesAppUnitTest {
        
    @Captor
    private ArgumentCaptor<Cliente> captor;
    
    @Mock
    private ClienteRepository clienteRepository;
    
    @InjectMocks
    private ClienteService clienteService;
    
    @Test
    public void testRegistrarCliente(){
        //given
        Cliente cliente = new Cliente();
        ClienteDTO clienteDTO = new ClienteDTO();
        cliente.setContrasenia("1234");
        cliente.setDireccion("Quito SN");
        cliente.setEdad(62);
        cliente.setEstado(true);
        cliente.setGenero("Masculino");
        cliente.setIdentificacion("111");
        cliente.setNombre("Isaac Asimov");
        cliente.setTelefono("02555");
        
        BeanUtils.copyProperties(cliente, clienteDTO);
        given(clienteRepository.save(any(Cliente.class))).willReturn(cliente);
        
        //when
        ClienteDTO cli = clienteService.registrarCliente(clienteDTO);
        
        //then
        then(clienteRepository).should().save(captor.capture());
        assertThat(captor.getValue().getNombre()).isEqualTo(cli.getNombre());
    }
}
