/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceA.dtos;

import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public abstract class PersonaDTO {
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
