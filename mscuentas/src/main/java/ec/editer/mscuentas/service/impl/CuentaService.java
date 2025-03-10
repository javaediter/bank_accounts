/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.service.impl;

import ec.editer.mscuentas.dtos.CuentaDTO;
import ec.editer.mscuentas.model.Cuenta;
import ec.editer.mscuentas.repository.CuentaRepository;
import ec.editer.mscuentas.service.ICuentaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Edison Teran
 */
@RequiredArgsConstructor
@Service
public class CuentaService implements ICuentaService{
    
    @Value("${api.server.clientes}")
    private String apiServerClientes;
        
    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CuentaDTO> cuentasPorCliente(Integer clienteId) {
        List<CuentaDTO> cuentas = new ArrayList<>();
        String nombreCliente = restTemplate.getForObject(apiServerClientes + "/nombre-cliente?clienteId=" + clienteId, String.class);
        cuentaRepository.findAllByClienteId(clienteId).forEach(x -> {
            CuentaDTO cuentaDTO = new CuentaDTO();
            BeanUtils.copyProperties(x, cuentaDTO);
            cuentaDTO.setNombreCliente(nombreCliente);
            cuentas.add(cuentaDTO);
        });
        return cuentas;
    }

    @Override
    public CuentaDTO registrarCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        BeanUtils.copyProperties(cuentaDTO, cuenta);
        cuenta.setCuentaId(null);
        cuentaRepository.save(cuenta);
        cuentaDTO.setCuentaId(cuenta.getCuentaId());
        String nombreCliente = restTemplate.getForObject(apiServerClientes + "/nombre-cliente?clienteId=" + cuentaDTO.getClienteId(), String.class);
        cuentaDTO.setNombreCliente(nombreCliente);
        return cuentaDTO;
    }

    @Override
    public CuentaDTO actualizarCuenta(CuentaDTO cuentaDTO) {
        Optional<Cuenta> opt = cuentaRepository.findByNumeroAndTipoCuenta(cuentaDTO.getNumero(), cuentaDTO.getTipoCuenta());
        if(opt.isPresent()){
            Cuenta cuenta = opt.get();
            cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
            cuenta.setEstado(cuentaDTO.isEstado());
            cuentaRepository.save(cuenta);
            CuentaDTO dto = new CuentaDTO();        
            BeanUtils.copyProperties(cuenta, dto);
            String nombreCliente = restTemplate.getForObject(apiServerClientes + "/nombre-cliente?clienteId=" + cuentaDTO.getClienteId(), String.class);
            dto.setNombreCliente(nombreCliente);
            return dto;
        }else{
            return registrarCuenta(cuentaDTO);
        }
    }

    @Override
    public Optional<CuentaDTO> obtenerCuentaPorNumeroTipo(String numero, String tipoCuenta) {
        Optional<Cuenta> opt = cuentaRepository.findByNumeroAndTipoCuenta(numero, tipoCuenta);
        if(opt.isPresent()){
            CuentaDTO dto = new CuentaDTO();
            BeanUtils.copyProperties(opt.get(), dto);
            return Optional.of(dto);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void eliminarCuenta(Integer cuentaId) throws Exception{
        cuentaRepository.deleteById(cuentaId);
    }

    @Override
    public CuentaDTO obtenerCuentaPorCuentaId(Integer cuentaId) {
        CuentaDTO dto = new CuentaDTO();
        Optional<Cuenta> opt = cuentaRepository.findById(cuentaId);
        if(opt.isPresent()){
            BeanUtils.copyProperties(opt.get(), dto);
        }else{
            dto.setCuentaId(0);
            dto.setClienteId(0);
        }
        return dto;
    }

    @Override
    public List<Integer> obtenerClientesIds() {
        return cuentaRepository.obtenerClientesIds();
    }
    
}
