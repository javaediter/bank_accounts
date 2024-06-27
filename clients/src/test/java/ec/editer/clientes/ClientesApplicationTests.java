package ec.editer.clientes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import ec.editer.clientes.configuration.BeansConfiguration;
import ec.editer.clientes.model.Cliente;
import ec.editer.clientes.model.EstadoCliente;
import ec.editer.clientes.repository.ClienteRepository;
import ec.editer.clientes.repository.EstadoClienteRepository;
import ec.editer.clientes.service.IClienteService;
import ec.editer.clientes.service.impl.ClienteService;
import ec.editer.clientes.utils.EncriptadorBasico;
import ec.editer.commons.clientes.dtos.ClienteDTO;
import ec.editer.commons.clientes.dtos.EstadoClienteDTO;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.BDDMockito.*;
import org.mockito.Captor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BeansConfiguration.class, EstadoClienteRepository.class, ClienteService.class})
class ClientesApplicationTests {
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @MockBean
    private EstadoClienteRepository estadoClienteRepository;
    
    @MockBean
    private EncriptadorBasico encriptadorBasico;
    
    @MockBean
    private ClienteRepository clienteRepository;
    
    @Autowired
    private IClienteService clienteService;
    
    @Captor
    private ArgumentCaptor<Cliente> clienteCaptor;
    
    private Iterable<Cliente> clientes;
    
    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException{
        String json = "";
        Resource resource = resourceLoader.getResource("classpath:clientes.json"); 
        
        try{            
            json = Files.readString(resource.getFile().toPath());
        }catch(IOException ioex){
            ioex.printStackTrace();
        } 
        
        ObjectMapper mapper = new JsonMapper();        
        Cliente[] list = mapper.readValue(json, Cliente[].class);
        
        log.info("total clientes: {}", list.length);
        clientes = Arrays.asList(list);
    }
    
    @Test
    public void obtenerClientesTest(){
        //given
        given(clienteRepository.findAll()).willReturn(clientes);
        
        //when
        List<ClienteDTO> result = clienteService.obtenerClientes();
        
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isPositive();
        
        //then
        then(clienteRepository).should().findAll();
    }
    
    @Test
    public void registrarClienteTest(){
        //given
        EstadoClienteDTO estadoDTO = EstadoClienteDTO.builder().estadoClienteId(1).valor(true).build();
        
        ClienteDTO dto = ClienteDTO.builder()
                .clienteId(3)
                .direccion("")
                .edad(28)
                .genero("F")
                .identificacion("911")
                .nombre("Guest")
                .password("abc123")
                .direccion("Ahi")
                .telefono("+593")
                .estadoCliente(estadoDTO)
                .build();
                
        Cliente cliente = new Cliente();
        EstadoCliente estadoCliente = new EstadoCliente();
        
        BeanUtils.copyProperties(dto, cliente);
        BeanUtils.copyProperties(estadoDTO, estadoCliente);
        
        cliente.setEstadoCliente(estadoCliente);
        
        given(clienteRepository.save(clienteCaptor.capture())).willReturn(cliente);
        given(encriptadorBasico.encriptador(dto.getPassword())).willReturn("xxx");
        
        //when
        clienteService.registrarCliente(dto);        
        assertThat(clienteCaptor.getValue().getClienteId()).isEqualTo(3);
        
        //then
        then(encriptadorBasico).should().encriptador(anyString());
        then(clienteRepository).should().save(any(Cliente.class));
    }
}
