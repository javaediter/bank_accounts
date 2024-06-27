/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.gateway.request;

import ec.editer.gateway.enums.APIPaths;
import ec.editer.gateway.enums.HttpVerbs;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edison Teran
 */
@Component
@NoArgsConstructor
@Data
public class Request implements Serializable{
    private HttpVerbs httpVerb;
    private APIPaths pathAPI;
    private Object body;
}
