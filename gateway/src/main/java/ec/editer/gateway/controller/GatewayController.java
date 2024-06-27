/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.gateway.controller;

import ec.editer.commons.clientes.dtos.ClienteDTO;
import ec.editer.commons.clientes.dtos.ClienteDataParcial;
import ec.editer.gateway.enums.APIPaths;
import ec.editer.gateway.enums.HttpVerbs;
import ec.editer.gateway.proxy.Proxy;
import ec.editer.gateway.request.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/gateway")
@RestController
public class GatewayController {
    
    @Autowired
    @Qualifier(value = "proxy")
    private Proxy proxy;
    
    @Autowired
    private Request request;
    
    @GetMapping(value = "/clientes/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClientes(){
        log.info("getClientes");
        request.setHttpVerb(HttpVerbs.GET);
        request.setPathAPI(APIPaths.GET_ALL_CLIENTES);
        request.setBody(null);        
        return proxy.requestClientes(request);
    }
    
    @PostMapping(value = "/clientes/registrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postCliente(@RequestBody ClienteDTO clienteDTO){
        log.info("postCliente");
        request.setHttpVerb(HttpVerbs.POST);
        request.setPathAPI(APIPaths.SAVE_CLIENTES);
        request.setBody(clienteDTO);        
        return proxy.requestClientes(request);
    }
    
    @PatchMapping(value = "/clientes/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchCliente(@RequestBody ClienteDataParcial dataParcial){
        log.info("patchCliente");
        request.setHttpVerb(HttpVerbs.PATCH);
        request.setPathAPI(APIPaths.UPDATE_CLIENTES);
        request.setBody(dataParcial);        
        return proxy.requestClientes(request);
    }
    
    @GetMapping(value = "/clientes/cliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClientePorId(@PathVariable("id") Integer clienteId){
        log.info("getClientePorId");
        request.setHttpVerb(HttpVerbs.GET);
        request.setPathAPI(APIPaths.GET_CLIENTE_BY_ID);
        request.setBody(clienteId);        
        return proxy.requestClientes(request);
    }
    
    @DeleteMapping(value = "/clientes/cliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCliente(@PathVariable("id") Integer clienteId){
        log.info("deleteCliente");
        request.setHttpVerb(HttpVerbs.DELETE);
        request.setPathAPI(APIPaths.GET_CLIENTE_BY_ID);
        request.setBody(clienteId);        
        return proxy.requestClientes(request);
    }
}
