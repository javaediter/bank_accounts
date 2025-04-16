/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.controller;

import ec.editer.msusuarios.dto.IngresoDTO;
import ec.editer.msusuarios.dto.TokenDTO;
import ec.editer.msusuarios.service.ISesionService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RestController
@RequestMapping("/api/home")
public class HomeController {
    
    @Autowired
    private ISesionService sesionService;
    
    @GetMapping("/login")
    public ResponseEntity showLogin(){
        log.info("-----> login");
        return ResponseEntity.ok("PAGE LOGIN");
    }
    
    @GetMapping("/auth")
    public ResponseEntity login(@RequestParam(name = "user", required = true) String username, @RequestParam(value = "pass", required = true) String password){
        log.info("-----> login " + username);
        IngresoDTO ingreso = new IngresoDTO(username, password);
        Optional<TokenDTO> opt = sesionService.autenticarUsuario(ingreso);
        if(opt.isPresent()){
            return ResponseEntity.ok(opt.get());
        }else{
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }
}
