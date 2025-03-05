/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 *
 * @author Edison Teran
 */
@Configuration
public class MSReportesConfiguration {
    
    @Bean
    public TopicExchange topicExchange(@Value("${amqp.banca.topic.name}") String topicExchangeName){
        return ExchangeBuilder.topicExchange(topicExchangeName).durable(true).build();
    }
    
    @Bean
    public Queue queueReportes(@Value("${amqp.banca.queue.name}") String queueName){
        return QueueBuilder.durable(queueName).build();
    }
    
    @Bean
    public Binding bindingReportes(final TopicExchange topicExchange, final Queue queueReportes){
        return BindingBuilder.bind(queueReportes).to(topicExchange).with("#.id");
    }
    
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory methodFactory = new DefaultMessageHandlerMethodFactory();
        MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        methodFactory.setMessageConverter(jsonConverter);
        return methodFactory;
    }
    
    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(final MessageHandlerMethodFactory messageHandlerMethodFactory){
        return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }
}
