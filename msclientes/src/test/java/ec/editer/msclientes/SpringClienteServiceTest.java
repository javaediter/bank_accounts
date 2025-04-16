/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes;

import ec.editer.msclientes.dtos.ClienteDTO;
import ec.editer.msclientes.enums.GeneroEnum;
import ec.editer.msclientes.model.Cliente;
import ec.editer.msclientes.repository.ClienteRepository;
import ec.editer.msclientes.service.impl.ClienteService;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Captor;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 *
 * @author Edison Teran
 */
@SpringBootTest(classes = {ClienteService.class})
public class SpringClienteServiceTest {
    
    @Captor
    ArgumentCaptor<Cliente> captor;
    
    @MockitoBean
    ClienteRepository clienteRepository;
    
    @Autowired
    ClienteService clienteService;
    
    @DisplayName("Registrar Cliente con SpringBootTest")
    @Test
    public void testRegistrarCliente(){
        //setting data and mock        
        ClienteDTO dto = new ClienteDTO();
        dto.setContrasenia("1234");
        dto.setDireccion("Quito SN");
        dto.setEdad(62);
        dto.setEstado(true);
        dto.setGenero("Masculino");
        dto.setIdentificacion("111");
        dto.setNombre("Isaac Asimov");
        dto.setTelefono("02555");  
        Cliente entidad = new Cliente();
        BeanUtils.copyProperties(dto, entidad);
        entidad.setClienteId(1);
        when(clienteRepository.save(captor.capture())).thenReturn(entidad);
        
        //when
        clienteService.registrarCliente(dto);
        
        //then
        assertThat(dto.getClienteId()).isEqualTo(1);
        assertEquals(captor.getValue().getGenderEnum().name(), GeneroEnum.MASCULINO.name());
        verify(clienteRepository).save(any(Cliente.class));       
    }
    
    @DisplayName("Obtener Cliente Por Id con SpringBootTest")
    @Test
    public void testObtenerClientePorId(){
        //setting data and mock          
        Cliente entidad = new Cliente();
        entidad.setContrasenia("1234");
        entidad.setDireccion("Quito SN");
        entidad.setEdad(62);
        entidad.setEstado(true);
        entidad.setGenderEnum(GeneroEnum.MASCULINO);
        entidad.setIdentificacion("111");
        entidad.setNombre("Isaac Asimov");
        entidad.setTelefono("02555");
        entidad.setClienteId(1);
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(entidad));
        
        Optional<ClienteDTO> opt = clienteService.obtenerClientePorId(1);
        
        assertAll(
                ()-> assertNotNull(opt.get()),
                ()-> assertEquals(opt.get().getClienteId(), 1)
        );
        verify(clienteRepository).findById(anyInt());
    }
}
