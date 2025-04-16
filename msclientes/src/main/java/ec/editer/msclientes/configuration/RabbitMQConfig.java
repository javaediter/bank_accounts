/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.configuration;

import ec.editer.msclientes.dtos.TokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class RabbitMQConfig {
    
    @Value("${amqp.token.topic.name}") 
    private String topicExchangeName;
    
    @Value("${amqp.token.queue.name}") 
    private String queueName;
    
    @Value("${local.file.path.tokens}")
    private String filePathName;
    
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public TopicExchange topicExchange(){
        return ExchangeBuilder.topicExchange(topicExchangeName).durable(true).build();
    }
    
    @Bean
    public Queue queueToken(){
        return QueueBuilder.durable(queueName).build();
    }
    
    @Bean
    public Binding bindingToken(final TopicExchange topicExchange, final Queue queueToken){
        return BindingBuilder.bind(queueToken).to(topicExchange).with("#.tkn");
    }
    
    @Bean
    public SimpleMessageListenerContainer listenerQueueReporte(final ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queueToken());
        container.setConcurrentConsumers(3);
        return container;
    }
    
    @Bean
    public MessageChannel queueInputChannel(){
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow queueTokenFlow(final SimpleMessageListenerContainer listenerQueueReporte){
        return IntegrationFlow
                .from(Amqp.inboundAdapter(listenerQueueReporte))
                .transform(Transformers.fromJson(TokenDTO.class))
                .handle("tokenService", "addCustomHeaders")
                .get();
    }
    
    @Bean
    public IntegrationFlow fileTokenFlow(){
        return IntegrationFlow
                .from("queueInputChannel")
                .transform(Transformers.toJson())
                .enrichHeaders(m -> m.headerExpression(FileHeaders.FILENAME, "headers['fileName']")
                                     .header("directory", filePathName))
                .handle(Files.outboundAdapter(m -> m.getHeaders().get("directory"))
                        .autoCreateDirectory(true)
                        .fileExistsMode(FileExistsMode.IGNORE))
                .get();
    }
}
