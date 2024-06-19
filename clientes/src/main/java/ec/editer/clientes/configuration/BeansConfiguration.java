/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.configuration;

import ec.editer.clientes.utils.EncriptadorBasico;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Edison Teran
 */
@Configuration
public class BeansConfiguration {
    
    @Bean
    public EncriptadorBasico getEncriptadorBasico(){
        return new EncriptadorBasico();
    }
}
