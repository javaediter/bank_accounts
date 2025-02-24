/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author Edison Teran
 */
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@Data
public abstract class PersonaDTO {
    public String nombre;
    public String genero;
    public Integer edad;
    public String identificacion;
    public String direccion;
    public String telefono;
}
