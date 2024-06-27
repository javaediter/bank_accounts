/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.service.impl;

import ec.editer.commons.cuentas.dtos.CuentaDTO;
import ec.editer.commons.cuentas.dtos.CuentaDataParcial;
import ec.editer.commons.cuentas.dtos.EstadoCuentaDTO;
import ec.editer.commons.cuentas.dtos.TipoCuentaDTO;
import ec.editer.cuentas.model.Cuenta;
import ec.editer.cuentas.model.EstadoCuenta;
import ec.editer.cuentas.model.TipoCuenta;
import ec.editer.cuentas.repository.CuentaRepository;
import ec.editer.cuentas.repository.EstadoCuentaRepository;
import ec.editer.cuentas.service.ICuentaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Service
public class CuentaService implements ICuentaService{
    
    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Autowired
    private EstadoCuentaRepository estadoCuentaRepository;
    
    @Override
    public Optional<CuentaDTO> registrarCuenta(CuentaDTO cuentaDTO) throws DataIntegrityViolationException{  
        cuentaDTO.setSaldoDisponible(cuentaDTO.getSaldoInicial());
        Cuenta cuenta = new Cuenta();
        BeanUtils.copyProperties(cuentaDTO, cuenta);
        
        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setTipoCuentaId(cuentaDTO.getTipoCuenta().getTipoCuentaId());
        cuenta.setTipoCuenta(tipoCuenta);
        
        EstadoCuenta estadoCuenta = new EstadoCuenta();
        estadoCuenta.setEstadoCuentaId(cuentaDTO.getEstadoCuenta().getEstadoCuentaId());
        cuenta.setEstadoCuenta(estadoCuenta);
        
        cuentaRepository.save(cuenta);
        cuentaDTO.setCuentaId(cuenta.getCuentaId());
        
        return Optional.of(cuentaDTO);
    }

    @Override
    public List<CuentaDTO> obtenerCuentasPorCliente(Integer clienteId) {
        List<CuentaDTO> cuentas = new ArrayList<>();
        cuentaRepository.findAllByClienteId(clienteId).forEach(x -> {
            CuentaDTO cuentaDTO = new CuentaDTO();
            BeanUtils.copyProperties(x, cuentaDTO);

            cuentaDTO.setTipoCuenta(new TipoCuentaDTO());
            BeanUtils.copyProperties(x.getTipoCuenta(), cuentaDTO.getTipoCuenta());
            
            cuentaDTO.setEstadoCuenta(new EstadoCuentaDTO());
            BeanUtils.copyProperties(x.getEstadoCuenta(), cuentaDTO.getEstadoCuenta());
            
            cuentas.add(cuentaDTO);
        });
        return cuentas;
    }

    @Override
    public List<CuentaDTO> obtenerCuentasPorClienteYPorTipoCuenta(Integer clienteId, Integer tipoCuentaId) {
        List<CuentaDTO> cuentas = new ArrayList<>();
        cuentaRepository.findAllByClienteIdAndTipoCuentaTipoCuentaId(clienteId, tipoCuentaId).forEach(x -> {
            CuentaDTO cuentaDTO = new CuentaDTO();
            BeanUtils.copyProperties(x, cuentaDTO);

            cuentaDTO.setTipoCuenta(new TipoCuentaDTO());
            BeanUtils.copyProperties(x.getTipoCuenta(), cuentaDTO.getTipoCuenta());
            
            cuentaDTO.setEstadoCuenta(new EstadoCuentaDTO());
            BeanUtils.copyProperties(x.getEstadoCuenta(), cuentaDTO.getEstadoCuenta());
            
            cuentas.add(cuentaDTO);
        });
        return cuentas;
    }    

    @Override
    public Optional<CuentaDTO> actualizarCuenta(CuentaDataParcial cuentaDataParcial) {        
        Optional<Cuenta> optCuenta = cuentaRepository.findByNumeroAndTipoCuentaTipoCuentaId(cuentaDataParcial.getNumeroCuenta(), cuentaDataParcial.getTipoCuentaId());
        
        if(optCuenta.isEmpty()){
            return Optional.empty();
        }
        
        optCuenta.ifPresent(x -> {
            Optional<EstadoCuenta> optEstadoCuenta = estadoCuentaRepository.findById(cuentaDataParcial.getEstadoCuentaId());
            x.setEstadoCuenta(optEstadoCuenta.get());
            cuentaRepository.save(x);
        });       
        
        CuentaDTO cuentaDTO = new CuentaDTO();        
        BeanUtils.copyProperties(optCuenta.get(), cuentaDTO);
        
        return Optional.of(cuentaDTO);
    }

    @Override
    public BigDecimal obtenerSaldoDisponibleCuenta(Integer cuentaId) {
        BigDecimal saldo = cuentaRepository.findSaldoDisponibleByCuentaId(cuentaId);
        if(saldo != null){
            return saldo;
        }else{
            return new BigDecimal(0);
        }
    }

    @Override
    public BigDecimal actualizarSaldoDisponibleCuenta(BigDecimal nuevoSaldo, Integer cuentaId) {
        Optional<Cuenta> optCuenta = cuentaRepository.findById(cuentaId);
        
        if(optCuenta.isEmpty()){
            return new BigDecimal(0);
        }
        
        optCuenta.ifPresent(x -> {
            x.setSaldoDisponible(nuevoSaldo);
            cuentaRepository.save(x);
        });
        
        return optCuenta.get().getSaldoDisponible();
    }
}
