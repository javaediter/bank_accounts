/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.gateway.enums;

/**
 *
 * @author Edison Teran
 */
public enum APIPaths {
    SAVE_CLIENTES("/clientes/registrar"), 
    GET_ALL_CLIENTES("/clientes/"),
    GET_CLIENTE_BY_ID("/clientes/cliente/"),
    UPDATE_CLIENTES("/clientes/actualizar");
    
    private String value;
    
    private APIPaths(String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
}
