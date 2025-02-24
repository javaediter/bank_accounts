/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.controller;

import ec.editer.mscuentas.dtos.CuentaDTO;
import ec.editer.mscuentas.service.ICuentaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api/cuentas")
@CrossOrigin(origins = "*/*")
public class CuentaController {
    @Autowired
    private ICuentaService cuentaService;
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCuenta(@Valid @RequestBody CuentaDTO cuentaDTO){
        log.info("registrarCuenta");
        try{
            return new ResponseEntity<>(cuentaService.registrarCuenta(cuentaDTO), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            log.error(String.format("ERROR: %s", e.getMessage()));
            return new ResponseEntity<>(String.format("Entradas duplicadas (numero %s y tipo de cuenta %s)", cuentaDTO.getNumero(), cuentaDTO.getTipoCuenta()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    @GetMapping("/por_cliente")
    public ResponseEntity<?> obtenerCuentasPorCliente(@RequestParam("cliente_id") Integer clienteId){
        log.info("obtenerCuentasPorCliente");
        return new ResponseEntity<>(cuentaService.cuentasPorCliente(clienteId), HttpStatus.OK);
    }
    
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarCuenta(@Valid @RequestBody CuentaDTO cuentaDTO){
        log.info("actualizarCuenta");
        return new ResponseEntity<>(cuentaService.actualizarCuenta(cuentaDTO), HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarCuenta(@RequestParam("cuenta_id") Integer cuentaId){
        log.info("eliminarCuenta");
        try{
            cuentaService.eliminarCuenta(cuentaId);
            return new ResponseEntity<>("Registro eliminado correctamente", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("La cuenta no puede ser eliminada porque tiene movimientos", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
