/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.dtos;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public abstract class PersonaDTO implements Serializable{
    protected String nombre;
    protected String genero;
    protected Integer edad;
    protected String identificacion;
    protected String direccion;
    protected String telefono;
}
