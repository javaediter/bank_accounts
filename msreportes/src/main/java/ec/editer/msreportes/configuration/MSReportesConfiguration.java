/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.configuration;

import ec.editer.msreportes.json.Transaccion;
import ec.editer.msreportes.model.Reporte;
import ec.editer.msreportes.repository.ReporteRepository;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class MSReportesConfiguration {
    
    @Value("${amqp.banca.queue.name}")
    private String queueName;
    
    @Value("${amqp.banca.topic.name}") 
    private String topicExchangeName;
    
    @Autowired
    private ReporteRepository reporteRepository;
    
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public TopicExchange topicExchange(){
        return ExchangeBuilder.topicExchange(topicExchangeName).durable(true).build();
    }
    
    @Bean
    public Queue queueReportes(){
        return QueueBuilder.durable(queueName).build();
    }
    
    @Bean
    public Binding bindingReportes(){
        return BindingBuilder.bind(queueReportes()).to(topicExchange()).with("#.id");
    }
        
    @Bean
    public SimpleMessageListenerContainer listenerQueueReportes(final ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.addQueues(queueReportes());
        container.setConcurrentConsumers(2);
        return container;
    }
    
    @Bean
    public IntegrationFlow listenerQueueReporteFlow(final SimpleMessageListenerContainer listenerQueueReportes){
        return IntegrationFlow
                .from(Amqp.inboundAdapter(listenerQueueReportes))
                .transform(Transformers.fromJson(Transaccion.class))
                .transform((Transaccion tx) -> {
                    return Reporte.builder().clienteId(tx.getClienteId()).fecha(new Date()).jsonContenido(tx.toJson()).build();
                })
                .transform((Reporte rep) -> reporteRepository.save(rep))
                .handle(m -> System.out.println(((Reporte)m.getPayload()).getId()))
                .get();
    }
}
