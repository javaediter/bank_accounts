/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.model;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@MappedSuperclass
@Data
public abstract class Persona implements Serializable{   
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
