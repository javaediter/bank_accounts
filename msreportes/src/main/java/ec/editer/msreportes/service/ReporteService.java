/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.service;

import ec.editer.msreportes.model.Reporte;
import ec.editer.msreportes.repository.ReporteRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class ReporteService {
    
    @Autowired
    private ReporteRepository reporteRepository;
    
    public Optional<Reporte> obtenerUltimoReporte(Integer clienteId){
        Reporte reporte = reporteRepository.findFirstByClienteIdOrderByFechaDesc(clienteId);
        if(reporte == null){
            return Optional.empty();
        }else{
            return Optional.of(reporte);
        }
    }
    
    public Page<Reporte> obtenerReportesPorClienteId(Integer clienteId, Pageable pePageable){
        return reporteRepository.findAllByClienteId(clienteId, pePageable);
    }

}
