/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.editer.msreportes.json.Transaccion;
import ec.editer.msreportes.model.Reporte;
import ec.editer.msreportes.repository.ReporteRepository;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ReporteService {
    
    private final ReporteRepository reporteRepository;
    
    public Optional<Reporte> obtenerUltimoReporte(Integer clienteId){
        List<Reporte> reportes = reporteRepository.findAllByClienteIdOrderByFechaDesc(clienteId);
        if(reportes.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(reportes.get(0));
        }
    }
    
    public Page<Reporte> obtenerReportesPorClienteId(Integer clienteId, Pageable pePageable){
        return reporteRepository.findAllByClienteId(clienteId, pePageable);
    }
    
    @RabbitListener(queues = "${amqp.banca.queue.name}")
    public void converterToReporte(Message message){
        try {
            log.info("----- converterToReporte -----");
            String routingKey = message.getMessageProperties().getReceivedRoutingKey();
            log.info(String.format("routingKey = %s", routingKey));
            ObjectMapper mapper = new ObjectMapper();
            Transaccion transaccion = mapper.readValue(message.getBody(), Transaccion.class);
            String jsonReporte = new String(message.getBody());
            Reporte reporte = new Reporte();
            reporte.setClienteId(transaccion.getClienteId());
            reporte.setJsonContenido(jsonReporte);
            reporte.setFecha(new Date());
            reporteRepository.save(reporte);
        } catch (IOException ex) {
            Logger.getLogger(ReporteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
