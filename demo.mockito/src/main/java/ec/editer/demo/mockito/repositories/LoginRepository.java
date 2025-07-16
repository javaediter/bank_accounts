/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito.repositories;

import ec.editer.demo.mockito.model.UserForm;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edison Teran
 */
@Component
public class LoginRepository {
    private final Map<String, String> users = new HashMap<>();
    
    @PostConstruct
    public void initData(){
        users.put("user01", "123456");
        users.put("user02", "123456");
        users.put("user03", "123456");
    }
    
    public List<String> getUsers(){
        List<String> list = new ArrayList<>();
        users.forEach((user, paswd) -> list.add(user));
        return list;
    }
    
    public Optional<Map<String, String>> getUserByCredentials(UserForm userForm){
        if(users.containsKey(userForm.getUsername())){
            String pwd = users.get(userForm.getUsername());
            if(pwd.equals(userForm.getPassword())){
                return Optional.of(Collections.singletonMap(userForm.getUsername(), users.get(userForm.getUsername())));
            }else{
                return Optional.empty();
            }            
        }else{
            return Optional.empty();
        }
    }
}
