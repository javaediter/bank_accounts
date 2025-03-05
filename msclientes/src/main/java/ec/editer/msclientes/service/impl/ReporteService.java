/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.service.impl;

import ec.editer.msclientes.reporte.Registro;
import ec.editer.msclientes.reporte.Reporte;
import ec.editer.msclientes.service.IReporteService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Edison Teran
 */
@Service
public class ReporteService implements IReporteService{
    
    @Value("${api.server.reportes}")
    private String ApiServerReportes;
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void escribirReporteToCsv(PrintWriter writer ,Integer clienteId) {
        CSVPrinter cSVPrinter = null;
        Reporte reporte = restTemplate.getForObject(ApiServerReportes + "/ultimo-reporte/" + clienteId, Reporte.class);
        if(reporte != null){
            try {
                cSVPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("FECHA", "CLIENTE", "CUENTA", "TIPO", "ESTADO", "MOV", "SALDO"));
                for(Registro reg : reporte.getRegistros()){
                    cSVPrinter.printRecord(reg.getFecha(), reg.getCliente(), reg.getNumeroCuenta(), reg.getTipoCuenta(), reg.isEstado(), reg.getMovimiento(), reg.getSaldoDisponible());
                }
                cSVPrinter.flush();
                cSVPrinter.close();
            } catch (IOException ex) {
                Logger.getLogger(ReporteService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
