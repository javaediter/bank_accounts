/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class UsuarioDTO {
    private Integer id;
    private String username;
    private String password;
    private boolean active;
    private List<UsuarioRolDTO> roles;
}
