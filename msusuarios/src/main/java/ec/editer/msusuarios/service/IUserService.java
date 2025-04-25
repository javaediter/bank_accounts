/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.service;

import ec.editer.msusuarios.dto.UsuarioDTO;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface IUserService {
    Optional<UsuarioDTO> getUserById(Integer id);
    List<UsuarioDTO> getAll();
    UsuarioDTO create(UsuarioDTO usuarioDTO);
    UsuarioDTO update(UsuarioDTO usuarioDTO);
    UsuarioDTO updatePassword(String username, String newPassword);
    void delete(Integer id);
}
