/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceA.configuration;

import ec.editer.microserviceA.utils.EncriptadorBasico;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Edison Teran
 */
@Configuration
public class EncriptadorConfiguration {
    
    @Bean
    public EncriptadorBasico getEncriptadorBasico(){
        return new EncriptadorBasico();
    }
}
