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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class ReporteService implements IReporteSevice{
    
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Transactional // Se agrega porque se llama a un SP
    @Override
    public List<Registro> construirReporte(Date fechaInicio, Date fechaFin, Integer clienteId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info(String.format("...construirReporte clienteId = %d, fechaInicio = %s, fechaFin = %s", clienteId, format.format(fechaInicio), format.format(fechaFin)));
        List<Registro> reporte = new ArrayList<>();
        movimientoRepository.construirReporte(fechaInicio, fechaFin, clienteId).forEach(x -> {
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
