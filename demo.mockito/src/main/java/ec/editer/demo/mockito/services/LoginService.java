/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito.services;

import ec.editer.demo.mockito.model.UserForm;
import ec.editer.demo.mockito.repositories.LoginRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@RequiredArgsConstructor
@Service
public class LoginService {
    
    private final LoginRepository loginRepository;
    
    public List<String> getAll(){
        return loginRepository.getUsers();
    }
    
    public boolean getUserByCredentials(UserForm userForm){
        return loginRepository.getUserByCredentials(userForm).isPresent();
    }
}
