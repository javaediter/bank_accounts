/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class TipoCuentaDTO {
    private Integer tipoCuentaId;
    private String nombre;
}
