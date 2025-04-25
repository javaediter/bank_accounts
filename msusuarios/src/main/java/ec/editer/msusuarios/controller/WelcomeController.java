/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.controller;

import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RestController
@RequestMapping("/welcome")
public class WelcomeController {
    
    @GetMapping
    public Map<String, String> welcome(){
        log.info("-----> welcome");
        return Collections.singletonMap("msg", "PAGE WELCOME");
    }
}
