/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito.controllers;

import ec.editer.demo.mockito.model.UserForm;
import ec.editer.demo.mockito.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    private final LoginService loginService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserForm userForm){
        if(loginService.getUserByCredentials(userForm)){
            return ResponseEntity.ok("Welcome " + userForm.getUsername());
        }else{
            return new ResponseEntity("User not found!", HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(loginService.getAll());
    }
}
