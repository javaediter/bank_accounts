/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.controller;

import ec.editer.microserviceB.repository.ReporteRepository;
import ec.editer.microserviceB.service.IReporteSevice;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*/*")
@RequestMapping("/reporte")
public class ReporteController {
    
    @Autowired
    private ReporteRepository reporteRepository;
    
    @Autowired
    private IReporteSevice reporteService;
    
    @GetMapping
    public ResponseEntity<?> obtenerMovimientosPorCliente(@RequestParam("cliente_id") Integer clienteId, @RequestParam("fecha_inicio") String fechaInicio, @RequestParam("fecha_fin") String fechaFin) throws ParseException {
        log.info("obtenerMovimientosPorCliente");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaStart = dateFormat.parse(fechaInicio);
        Date fechaEnd = dateFormat.parse(fechaFin);
        java.sql.Date fechaStartSQL = new java.sql.Date(fechaStart.getTime());
        java.sql.Date fechaEndSQL = new java.sql.Date(fechaEnd.getTime());
        return new ResponseEntity<>(reporteService.construirReporte(clienteId, fechaStartSQL, fechaEndSQL), HttpStatus.OK);
    }
}
