/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import ec.editer.demo.mockito.controllers.LoginController;
import ec.editer.demo.mockito.model.UserForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest // automáticamente busca e inicializa el ApplicationContext
public class ControllerSpringBootTest {
    
    @Autowired
    private LoginController loginController;
    
    @Test
    public void testLoginOkController(){
        UserForm userForm = new UserForm("user01", "123456");
        ResponseEntity<String> response = loginController.login(userForm);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void testLoginErrorController(){
        UserForm userForm = new UserForm("user01", "1234560");
        ResponseEntity<String> response = loginController.login(userForm);
        Assertions.assertEquals(404, response.getStatusCode().value());
    }
}
