/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.service;

import ec.editer.microserviceB.report.Reporte;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Edison Teran
 */
public interface IReporteSevice {
    public List<Reporte> construirReporte(Integer clienteId, Date fechaInicio, Date fechaFin);
}
