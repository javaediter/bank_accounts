/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service.impl;

import ec.editer.mscuentas.reporte.Registro;
import ec.editer.mscuentas.reporte.Reporte;
import ec.editer.mscuentas.service.ICuentaService;
import ec.editer.mscuentas.service.IReporteServiceJob;
import ec.editer.mscuentas.service.IReporteSevice;
import ec.editer.mscuentas.service.amqp.IReportePublisher;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class ReporteServiceJob implements IReporteServiceJob{
    
    @Autowired
    private ICuentaService cuentaService;
    
    @Autowired
    private IReporteSevice reporteSevice;
    
    @Autowired
    private IReportePublisher reportePublisher;

    @Scheduled(cron = "#{@environment.getProperty('cron.expression.reportes')}")
    @Override
    public void enviarReportesClientes() {
        log.info("enviarReportesClientes");
        Calendar calendar = Calendar.getInstance();
        Date fin = new Date(calendar.getTime().getTime());
        calendar.add(Calendar.MONTH, -1);
        Date inicio = calendar.getTime();
        
        java.sql.Date fechaInicio = new java.sql.Date(inicio.getTime());
        java.sql.Date fechaFin = new java.sql.Date(fin.getTime());
        
        List<Integer> clientesIds = cuentaService.obtenerClientesIds();
        clientesIds.forEach(id -> {
            List<Registro> registros = reporteSevice.construirReporte(id, fechaInicio, fechaFin);
            if(!registros.isEmpty()){
                Reporte reporte = new Reporte(id, registros);
                reportePublisher.publicarReporte(reporte);
            }            
        });
    }
    
}
