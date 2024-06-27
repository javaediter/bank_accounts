/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.gateway.proxy;

import ec.editer.commons.clientes.dtos.ClienteDTO;
import ec.editer.commons.clientes.dtos.ClienteDataParcial;
import ec.editer.gateway.enums.APIPaths;
import ec.editer.gateway.request.Request;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Component(value = "handlerRequestImpl")
public class HandlerRequestImpl implements HandlerRequest {
    
    @Value("${app.clientes.server}")
    private String ServerClientes;
    
    @Value("${app.cuentas.server}")
    private String ServerCuentas;
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity requestClientes(Request request) {
        log.info("Making a request for clientes");
        ResponseEntity<?> response = null;
        URI uri = null;
        try {
            String url = ServerClientes + (request.getBody() instanceof Integer ? request.getPathAPI().getValue() + (Integer)request.getBody() : request.getPathAPI().getValue());
            uri = new URI(url);
            
            switch(request.getHttpVerb()){
                case GET:
                    if(request.getPathAPI().equals(APIPaths.GET_ALL_CLIENTES)){
                        response = restTemplate.getForEntity(uri, ArrayList.class);
                    }else if(request.getPathAPI().equals(APIPaths.GET_CLIENTE_BY_ID)){
                        response = restTemplate.getForEntity(url, String.class);
                    }
                    break;                             
                case POST:
                    response = restTemplate.postForEntity(uri, (ClienteDTO)request.getBody(), ClienteDTO.class);
                    break;
                case PATCH:
                    response = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>((ClienteDataParcial)request.getBody()), ClienteDTO.class);
                    break;
                case DELETE:
                    restTemplate.delete(uri);
                    break;
                default:
                    response = restTemplate.getForEntity(uri, ArrayList.class);
            }                    
        } catch (URISyntaxException | RestClientException ex) {
            log.error(ex.getMessage());
        }

        return response;
    }

    @Override
    public ResponseEntity requestCuentas(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
