/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Nombre es requerido")
    @NotBlank(message = "Nombre es requerido")
    public String nombre;
    
    @NotNull(message = "Genero es requerido")
    @NotBlank(message = "Genero es requerido")
    public String genero;
    
    @NotNull
    public Integer edad;
    
    @NotNull(message = "Identificacion es requerido")
    @NotBlank(message = "Identificacion es requerido")
    public String identificacion;
    
    @NotNull
    public String direccion;
    
    @NotNull
    public String telefono;
}
