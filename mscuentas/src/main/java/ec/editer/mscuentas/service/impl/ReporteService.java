/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service.impl;

import ec.editer.mscuentas.reporte.Registro;
import ec.editer.mscuentas.repository.MovimientoRepository;
import ec.editer.mscuentas.service.IReporteSevice;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Service
public class ReporteService implements IReporteSevice{
    
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Override
    public List<Registro> construirReporte(Integer clienteId, Date fechaInicio, Date fechaFin) {
        List<Registro> reporte = new ArrayList<>();
        movimientoRepository.construirReporte(clienteId, fechaInicio, fechaFin).forEach(x -> {
            Object[] array = (Object[])x;
            Registro rep = new Registro();
            rep.setFecha(array[0].toString());
            rep.setCliente(array[1].toString());
            rep.setNumeroCuenta(array[2].toString());
            rep.setTipoCuenta(array[3].toString());
            rep.setSaldoInicial((BigDecimal)array[4]);
            rep.setEstado((Boolean)array[5]);
            rep.setMovimiento((BigDecimal)array[6]);
            rep.setSaldoDisponible((BigDecimal)array[7]);
            reporte.add(rep);
        });
        return reporte;
    }
    
}
