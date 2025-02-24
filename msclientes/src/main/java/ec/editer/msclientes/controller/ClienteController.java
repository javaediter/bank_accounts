/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.controller;

import ec.editer.msclientes.dtos.ClienteDTO;
import ec.editer.msclientes.service.IClienteService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin("*/*")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;
    
    @GetMapping("/")
    public ResponseEntity<List<ClienteDTO>> obtenerClientes(){
        log.info("obtenerClientes");
        return new ResponseEntity<>(clienteService.obtenerTodos(), HttpStatus.OK);
    }
    
    @PostMapping(value = "/registrar")
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        log.info("registrarCliente");
        log.info(" clienteDTO {}", clienteDTO);
        try{
            return new ResponseEntity<>(clienteService.registrarCliente(clienteDTO), HttpStatus.CREATED);
        }catch(DataIntegrityViolationException e){
            log.error(String.format("ERROR %s", e.getMessage()));
            return new ResponseEntity<>(String.format("Identificacion %s duplicada", clienteDTO.getIdentificacion()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    @PutMapping(value = "/actualizar")
    public ResponseEntity<?> actualizacionCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        log.info("actualizacionCliente");
        Optional<ClienteDTO> opt = clienteService.obtenerClientePorId(clienteDTO.getClienteId());
        if(opt.isPresent()){
            return new ResponseEntity<>(clienteService.actualizarCliente(clienteDTO), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }       
    }
    
    @GetMapping(value = "/cliente/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable("id") Integer clienteId){
        log.info("obtenerClientePorId");
        Optional<ClienteDTO> opt = clienteService.obtenerClientePorId(clienteId);
        if(opt.isPresent()){
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarCliente(@RequestParam("cliente_id") Integer clienteId){
        log.info("eliminarCliente");
        try{
            clienteService.eliminarCliente(clienteId);
            return new ResponseEntity<>("Registro eliminado correctamente", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("El cliente no puede ser eliminado porque tiene cuentas relacionadas", HttpStatus.NOT_ACCEPTABLE);
        }      
    }
    
    @GetMapping("/obtenerNombreCliente")
    public ResponseEntity<?> obtenerNombreCliente(@RequestParam("cliente_id") Integer clienteId){
        log.info("obtenerNombreCliente");
        return new ResponseEntity<>(clienteService.obtenerNombreCliente(clienteId), HttpStatus.OK);
    }
}
