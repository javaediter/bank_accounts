/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaccion implements Serializable{
    private Integer clienteId;
    private List<Registro> registros;
    
    public String toJson(){
        try {
            ObjectMapper mapper = new JsonMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
