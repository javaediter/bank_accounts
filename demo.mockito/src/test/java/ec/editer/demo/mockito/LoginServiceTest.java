/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import ec.editer.demo.mockito.model.UserForm;
import ec.editer.demo.mockito.repositories.LoginRepository;
import ec.editer.demo.mockito.services.LoginService;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    
    @Mock
    private LoginRepository loginRepository;
    
    @InjectMocks
    private LoginService loginService;
    
    @Captor
    ArgumentCaptor<UserForm> argCapUserForm;
    
    @Test
    public void testLoginOk(){
        UserForm userForm = new UserForm("user01", "123456");
        Optional<Map<String, String>> userMap = Optional.of(Collections.singletonMap("user01", "123456"));
        when(loginRepository.getUserByCredentials(any(UserForm.class))).thenReturn(userMap);
        
        boolean loginSuccess = loginService.getUserByCredentials(userForm);
        verify(loginRepository).getUserByCredentials(argCapUserForm.capture());
        assertEquals(userForm.getUsername(), argCapUserForm.getValue().getUsername());
        assertTrue(loginSuccess);
    }
    
    @Test
    public void testLoginError(){
        UserForm userForm = new UserForm("user010", "123456");
        when(loginRepository.getUserByCredentials(any(UserForm.class))).thenReturn(Optional.empty());
        
        boolean loginSuccess = loginService.getUserByCredentials(userForm);
        verify(loginRepository).getUserByCredentials(argCapUserForm.capture());
        assertEquals(userForm.getUsername(), argCapUserForm.getValue().getUsername());
        assertFalse(loginSuccess);
    }
}
