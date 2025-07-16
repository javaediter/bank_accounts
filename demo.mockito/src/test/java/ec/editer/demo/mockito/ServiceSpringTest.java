/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import ec.editer.demo.mockito.model.UserForm;
import ec.editer.demo.mockito.repositories.LoginRepository;
import ec.editer.demo.mockito.services.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LoginRepository.class, LoginService.class})
public class ServiceSpringTest {
    
    @Autowired
    private LoginService loginService;
    
    @Test
    public void testService(){
        UserForm userForm = new UserForm("user01", "123456");
        boolean loginSuccess = loginService.getUserByCredentials(userForm);
        Assertions.assertTrue(loginSuccess);
    }
}
