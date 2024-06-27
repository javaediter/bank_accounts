/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.gateway.proxy;

import ec.editer.gateway.request.Request;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edison Teran
 */
@Component(value = "proxy")
@Data
public class Proxy implements HandlerRequest{
    
    @Autowired
    @Qualifier(value = "handlerRequestImpl")
    private HandlerRequest handler;

    @Override
    public ResponseEntity requestClientes(Request request) {
        return handler.requestClientes(request);
    }

    @Override
    public ResponseEntity requestCuentas(Request request) {
        return handler.requestCuentas(request);
    }
}
