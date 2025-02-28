/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service.amqp.impl;

import ec.editer.mscuentas.reporte.Registro;
import ec.editer.mscuentas.reporte.Reporte;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import ec.editer.mscuentas.service.amqp.IReportePublisher;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class ReportePublisher implements IReportePublisher{
    
    String nombre;
    
    @Value("${amqp.banca.topic.name}")
    private String topicExchangeName;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Se publica el mensaje hacia el broker
     * @param reporte 
     */
    @Override
    public void publicarReporte(Reporte reporte) {
        log.info(">------------------> publicarReporte <------------------<");
        String routingKey = "reportes.movimientos.json";
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, reporte);
    }
}
