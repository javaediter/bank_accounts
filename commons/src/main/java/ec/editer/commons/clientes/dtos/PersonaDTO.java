/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.commons.clientes.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author Edison Teran
 */
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
@SuperBuilder
@Data
public class PersonaDTO implements Serializable{
    public String nombre;
    public String genero;
    public Integer edad;
    public String identificacion;
    public String direccion;
    public String telefono;
}
