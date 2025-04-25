/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.editer.msusuarios.enums.RolEnum;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class RolDTO {
    private Integer id;
    @JsonProperty(value = "role")
    private RolEnum rolEnum;
    @JsonIgnore
    private List<UsuarioRolDTO> roles;
}
