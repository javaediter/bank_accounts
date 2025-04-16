/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.model;

import ec.editer.msclientes.enums.GeneroEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Edison Teran
 */
@MappedSuperclass
@EqualsAndHashCode
@Data
public abstract class Persona {
    protected String nombre;
    
    @Column(name = "genero")
    @Enumerated(EnumType.STRING)
    protected GeneroEnum genderEnum;
    
    protected Integer edad;
    protected String identificacion;
    protected String direccion;
    protected String telefono;
}
