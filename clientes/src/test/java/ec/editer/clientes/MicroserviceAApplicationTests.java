package ec.editer.clientes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import ec.editer.clientes.dtos.ClienteDTO;
import ec.editer.clientes.model.Cliente;
import ec.editer.clientes.repository.ClienteRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest
class MicroserviceAApplicationTests {
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @Mock
    private ClienteRepository clienteRepository;
    
    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException{
        StringBuilder json = new StringBuilder();
        Resource resource = resourceLoader.getResource("classpath:clientes.json"); 
        
        try(var br = new BufferedReader(new FileReader(resource.getFile()))){            
            String line = null;
            while((line = br.readLine()) != null){
                json.append(line);
            }
        }catch(IOException ioex){
            ioex.printStackTrace();
        }
        
        List<Cliente> clientes = new ArrayList<>();
        ObjectMapper mapper = new JsonMapper();       
        List<LinkedHashMap<String, Object>> map = mapper.readValue(json.toString(), ArrayList.class);
        
        map.forEach(x -> {
            Cliente cliente = new Cliente();
            x.forEach((key, value) -> {                
                if(key.equals("clienteId")){
                    cliente.setClienteId((Integer)value);
                }else if(key.equals("nombre")){
                    cliente.setNombre((String)value);
                }else if(key.equals("identificacion")){
                    cliente.setIdentificacion((String)value);
                }              
            });
            clientes.add(cliente);
        });
        
        BDDMockito.given(clienteRepository.findAll()).willReturn(clientes);
    }
    
    @Test
    public void obtenerClienteTest(){
        List<ClienteDTO> clientes = new ArrayList<>();
        Iterator<Cliente> iter = clienteRepository.findAll().iterator();
        while(iter.hasNext()){
            Cliente cliente = iter.next();
            ClienteDTO dto = new ClienteDTO();
            BeanUtils.copyProperties(cliente, dto);
            clientes.add(dto);
        }
        Assert.assertTrue(clientes.size() == 2);
        Assert.assertEquals("Juan Salinas", clientes.get(0).getNombre());
        Assert.assertEquals("0175", clientes.get(1).getIdentificacion());
    }
}
