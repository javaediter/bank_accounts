package ec.editer.cuentas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import ec.editer.cuentas.dtos.CuentaDTO;
import ec.editer.cuentas.model.Cuenta;
import ec.editer.cuentas.repository.CuentaRepository;
import ec.editer.cuentas.repository.EstadoCuentaRepository;
import ec.editer.cuentas.service.ICuentaService;
import ec.editer.cuentas.service.impl.CuentaService;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CuentaService.class})
class CuentasApplicationTests {
    
    @MockBean
    private CuentaRepository cuentaRepository;
    
    @MockBean
    private EstadoCuentaRepository estadoCuentaRepository;
    
    @Autowired
    private ICuentaService cuentaService;
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    private List<Cuenta> cuentas;
    
    @BeforeEach
    public void setUp() throws JsonProcessingException, IOException{
        String json = "";
        Resource resource = resourceLoader.getResource("classpath:cuentas.json");
        
        try{
            json = Files.readString(resource.getFile().toPath());          
        }catch(Exception e){
            log.error(e.getMessage());
        }
        
        ObjectMapper mapper = new JsonMapper();
        Cuenta[] list = mapper.readValue(json, Cuenta[].class);
        log.info("size {}", list.length);
        cuentas = Arrays.asList(list);
    }

    @Test
    public void obtenerCuentasPorClienteTest(){
        //given
        given(cuentaRepository.findAllByClienteId(anyInt())).willReturn(cuentas);
        
        //when
        List<CuentaDTO> result = cuentaService.obtenerCuentasPorCliente(1);
        assertThat(result.size()).isEqualTo(cuentas.size());
        
        //then
        then(cuentaRepository).should().findAllByClienteId(anyInt());
    }
}
