/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.gateway.proxy;

import ec.editer.gateway.request.Request;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Edison Teran
 */
public interface HandlerRequest {
    ResponseEntity requestClientes(Request request);
    ResponseEntity requestCuentas(Request request);
}
