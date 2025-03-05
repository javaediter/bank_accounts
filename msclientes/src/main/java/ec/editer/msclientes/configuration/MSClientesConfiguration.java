/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Edison Teran
 */
@Configuration
public class MSClientesConfiguration {
    
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
