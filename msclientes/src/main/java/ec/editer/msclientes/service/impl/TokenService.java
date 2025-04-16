/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.service.impl;

import ec.editer.msclientes.dtos.TokenDTO;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service("tokenService")
public class TokenService {
    
    @Autowired
    private MessageChannel queueInputChannel;
    
    public void addCustomHeaders(TokenDTO tokenDTO){
        log.info("//------------> addCustomHeaders");
        Message<TokenDTO> message = MessageBuilder.withPayload(tokenDTO).setHeader("fileName", new Date().getTime() + ".json").build();
        queueInputChannel.send(message);
    }
}
