/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.service;

import org.springframework.amqp.core.Message;

/**
 *
 * @author Edison Teran
 */
public interface IRabbitService {
    void converterToTokenDTO(Message message);
}
