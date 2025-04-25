/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.service;

import ec.editer.msusuarios.dto.UsuarioRolDTO;
import ec.editer.msusuarios.enums.RolEnum;
import java.util.List;

/**
 *
 * @author Edison Teran
 */
public interface IRoleService {
    List<UsuarioRolDTO> getAllByUserId(Integer userId);
    UsuarioRolDTO create(RolEnum rolEnum, Integer userId);
    void delete(Integer id);
}
