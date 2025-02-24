/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes;

import ec.editer.msclientes.dtos.ClienteDTO;
import ec.editer.msclientes.model.Cliente;
import ec.editer.msclientes.repository.ClienteRepository;
import ec.editer.msclientes.service.IClienteService;
import ec.editer.msclientes.service.impl.ClienteService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.Captor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ClienteRepository.class, ClienteService.class})
public class ClientesAppIntegrationTest {
    
    @MockitoBean
    private ClienteRepository clienteRepository;
    
    @Autowired
    private IClienteService clienteService;
    
    @Captor
    private ArgumentCaptor<Cliente> clienteCaptor;
    
    @Test
    public void testRegistrarCliente(){
        //given
        ClienteDTO dto = ClienteDTO.builder()
                .edad(38)
                .genero("Femenino")
                .identificacion("1714")
                .nombre("Eve Guerra")
                .contrasenia("abc123")
                .direccion("Carolina NE")
                .telefono("+593")
                .estado(true)
                .build();
        
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(dto, cliente);
        
        given(clienteRepository.save(clienteCaptor.capture())).willReturn(cliente);
        
        //when
        clienteService.registrarCliente(dto);        
        assertThat(clienteCaptor.getValue().getNombre()).isEqualTo("Eve Guerra");
        
        //then
        then(clienteRepository).should().save(any(Cliente.class));
    }
}
