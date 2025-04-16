/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.editer.msclientes.dtos.TokenDTO;
import ec.editer.msclientes.service.IRabbitService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class RabbitService implements IRabbitService{

    @RabbitListener(queues = "${amqp.token.queue.name}")
    @Override
    public void converterToTokenDTO(Message message) {
        log.info("...leyento message en converterToTokenDTO");
        ObjectMapper mapper = new ObjectMapper();
        try {
            TokenDTO tokenDTO = mapper.readValue(message.getBody(), TokenDTO.class);
            log.info("TOKEN: {}", tokenDTO.getToken());
        } catch (IOException ex) {
            Logger.getLogger(RabbitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
