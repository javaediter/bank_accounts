/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author Edison Teran
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class ClienteDTO extends PersonaDTO{
    @NotNull
    private Integer clienteId;

    @NotNull(message = "Contrasenia es requerido")
    @NotBlank(message = "Contrasenia es requerido")
    private String contrasenia;
    
    @NotNull
    private boolean estado;
}
