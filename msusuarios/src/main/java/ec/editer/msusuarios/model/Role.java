/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.model;

import ec.editer.msusuarios.enums.RolEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
@Entity
//@Access(AccessType.FIELD)// JPA accede a los valores desde las variables en vez de los getters/setters
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"nombre"}))
public class Role {
    
    @Id
    @Column(name = "rol_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre")
    @Enumerated(EnumType.STRING)
    private RolEnum rolEnum;
    
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    private List<UserRole> roles;
}
