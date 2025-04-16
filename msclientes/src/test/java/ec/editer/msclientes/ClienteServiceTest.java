package ec.editer.msclientes;

import ec.editer.msclientes.dtos.ClienteDTO;
import ec.editer.msclientes.enums.GeneroEnum;
import ec.editer.msclientes.model.Cliente;
import ec.editer.msclientes.repository.ClienteRepository;
import ec.editer.msclientes.service.impl.ClienteService;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;
import org.mockito.Captor;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    
    @Captor
    private ArgumentCaptor<Cliente> captor;
    
    @Mock
    private ClienteRepository clienteRepository;
    
    @InjectMocks
    private ClienteService clienteService;
    
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
    
    @Test
    public void testobtenerClientePorId(){
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
