/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceA.controller;

import ec.editer.microserviceA.dtos.ClienteDTO;
import ec.editer.microserviceA.dtos.ClienteDataParcial;
import ec.editer.microserviceA.service.IClienteService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*/*")
public class ClienteController {
    
    @Autowired
    private IClienteService clienteService;
    
    @GetMapping("/")
    public ResponseEntity<List<ClienteDTO>> obtenerClientes(){
        log.info("obtenerClientes");
        return new ResponseEntity<>(clienteService.obtenerClientes(), HttpStatus.OK);
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCliente(@RequestBody ClienteDTO clienteDTO){
        log.info("registrarCliente");
        try{
            return new ResponseEntity<>(clienteService.registrarCliente(clienteDTO), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            log.error(String.format("ERROR %s", e.getMessage()));
            return new ResponseEntity<>(String.format("Identificacion %s duplicada", clienteDTO.getIdentificacion()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    @PatchMapping("/actualizar")
    public ResponseEntity<?> actualizacionParcialCliente(@RequestBody ClienteDataParcial clienteDataParcial){
        log.info("actualizacionParcialCliente");
        Optional<ClienteDTO> opt = clienteService.obtenerClientePorId(clienteDataParcial.getClienteId());
        if(opt.isPresent()){
            return new ResponseEntity<>(clienteService.actualizacionDataParcial(clienteDataParcial).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }       
    }
    
    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable("id") Integer clienteId){
        log.info("obtenerClientePorId");
        Optional<ClienteDTO> opt = clienteService.obtenerClientePorId(clienteId);
        if(opt.isPresent()){
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable("id") Integer clienteId){
        log.info("eliminarCliente");
        try{
            clienteService.eliminarCliente(clienteId);
            return new ResponseEntity<>("Registro eliminado correctamente", HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }      
    }
}