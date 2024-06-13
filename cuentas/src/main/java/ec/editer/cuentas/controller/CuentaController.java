/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.cuentas.controller;

import ec.editer.cuentas.dtos.CuentaDTO;
import ec.editer.cuentas.dtos.CuentaDataParcial;
import ec.editer.cuentas.service.ICuentaService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RestController
@RequestMapping("/cuentas")
@CrossOrigin(origins = "*/*")
public class CuentaController {
    
    @Autowired
    private ICuentaService cuentaService;
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCuenta(@RequestBody CuentaDTO cuentaDTO){
        log.info("registrarCuenta");
        try{
            return new ResponseEntity<>(cuentaService.registrarCuenta(cuentaDTO).get(), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            log.error(String.format("ERROR: %s", e.getMessage()));
            return new ResponseEntity<>(String.format("Entradas duplicadas (numero %s y tipo de cuenta %d)", cuentaDTO.getNumero(), cuentaDTO.getTipoCuenta().getTipoCuentaId()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    @GetMapping("/cliente")
    public ResponseEntity<List<CuentaDTO>> obtenerCuentasPorCliente(@RequestParam("cliente_id") Integer clienteId){
        log.info("obtenerCuentasPorCliente");
        return new ResponseEntity<>(cuentaService.obtenerCuentasPorCliente(clienteId), HttpStatus.OK);
    }
    
    @GetMapping("/cliente-tipo-cuenta")
    public ResponseEntity<List<CuentaDTO>> obtenerCuentasPorClienteYPorTipo(@RequestParam("cliente_id") Integer clienteId,@RequestParam("tipo_cuenta_id") Integer tipoCuentaId){
        log.info("obtenerCuentasPorClienteYPorTipo");
        return new ResponseEntity<>(cuentaService.obtenerCuentasPorClienteYPorTipoCuenta(clienteId, tipoCuentaId), HttpStatus.OK);
    } 
    
    @PatchMapping("/actualizar")
    public ResponseEntity<?> actualizarCuenta(@RequestBody CuentaDataParcial cuentaDataParcial){
        log.info("actualizarCuenta");
        Optional<CuentaDTO> opt = cuentaService.actualizarCuenta(cuentaDataParcial);
        if(opt.isPresent()){
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(String.format("La cuenta número %s no existe", cuentaDataParcial.getNumeroCuenta()), HttpStatus.NOT_FOUND);
        }
    }

}
