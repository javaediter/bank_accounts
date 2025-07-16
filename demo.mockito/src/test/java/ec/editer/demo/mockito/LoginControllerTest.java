/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import ec.editer.demo.mockito.controllers.LoginController;
import ec.editer.demo.mockito.model.UserForm;
import ec.editer.demo.mockito.services.LoginService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    
    @Mock
    private LoginService loginService;
    
    @InjectMocks
    private LoginController loginController;
        
    @Test
    public void testLoginOk(){
        UserForm userForm = new UserForm("user01", "123456");
        when(loginService.getUserByCredentials(userForm)).thenReturn(Boolean.TRUE);
        
        ResponseEntity<String> responseLogin = loginController.login(userForm);       
        String response = responseLogin.getBody();
        System.out.println("response: " + response);
        
        assertEquals("Welcome user01", response);
        verify(loginService).getUserByCredentials(any(UserForm.class));
    }
    
    @Test
    public void testLoginUserError(){
        UserForm userForm = new UserForm("user010", "123456");
        when(loginService.getUserByCredentials(userForm)).thenReturn(Boolean.FALSE);
        
        ResponseEntity<String> responseLogin = loginController.login(userForm);
        String response = responseLogin.getBody();
        
        assertEquals("User not found!", response);
        verify(loginService).getUserByCredentials(any(UserForm.class));
    }
    
    @Test
    public void testLoginPasswordError(){
        UserForm userForm = new UserForm("user01", "1234560");
        when(loginService.getUserByCredentials(userForm)).thenReturn(Boolean.FALSE);
        
        ResponseEntity<String> responseLogin = loginController.login(userForm);
        String response = responseLogin.getBody();
        
        assertEquals("User not found!", response);
        verify(loginService).getUserByCredentials(any(UserForm.class));
    }
}
