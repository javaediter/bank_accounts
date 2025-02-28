/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service.amqp;

import ec.editer.mscuentas.reporte.Reporte;

/**
 *
 * @author Edison Teran
 */
public interface IReportePublisher {
    void publicarReporte(Reporte reporte);
}
