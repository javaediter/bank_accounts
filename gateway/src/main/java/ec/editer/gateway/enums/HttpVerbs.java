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
public enum HttpVerbs {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE"), PATCH("PATCH");
    
    private String value;
    
    private HttpVerbs(String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
}
