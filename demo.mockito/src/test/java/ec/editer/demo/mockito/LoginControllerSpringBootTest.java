/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.editer.demo.mockito.model.UserForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Edison Teran
 */
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerSpringBootTest {
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testGetAll() throws Exception{
        mockMvc.perform(get("/api/login/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void testLoginOk() throws Exception{
        UserForm userForm = new UserForm("user01", "123456");
        String json = mapper.writeValueAsString(userForm);
        System.out.println("JSON: " + json);
        mockMvc.perform(post("/api/login/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testLoginError() throws JsonProcessingException, Exception{
        UserForm userForm = new UserForm("user01", "1234560");
        String json = mapper.writeValueAsString(userForm);
        mockMvc.perform(post("/api/login/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isNotFound());
    }
}
