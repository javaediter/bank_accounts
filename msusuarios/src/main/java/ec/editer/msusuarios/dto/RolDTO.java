/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.dto;

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
    private RolEnum rolEnum;
    private List<UsuarioRolDTO> roles;
}
