/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import ec.editer.msreportes.json.Transaccion;
import ec.editer.msreportes.model.Reporte;
import ec.editer.msreportes.service.ReporteService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reporte")
@CrossOrigin("*/*")
@Slf4j
public class ReporteController {
    
    private final ReporteService reporteService;
    
    @GetMapping("/ultimo-reporte/{clienteId}")
    public ResponseEntity getUltimoReportePorClienteId(@PathVariable Integer clienteId) throws JsonProcessingException{
        log.info("--------------------> getUltimoReportePorClienteId para " + clienteId);
        Optional<Reporte> opt = reporteService.obtenerUltimoReporte(clienteId);
        if(opt.isPresent()){
            ObjectMapper objectMapper = new JsonMapper();
            Transaccion reporte = objectMapper.readValue(opt.get().getJsonContenido(), Transaccion.class);
            return ResponseEntity.ok(reporte);
        }else{
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).build();
        }
    }
    
    @GetMapping("/todos/{clienteId}")
    public ResponseEntity getTodosPorClienteId(@PathVariable Integer clienteId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(reporteService.obtenerReportesPorClienteId(clienteId, pageable));
    }
}
