/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.service.impl;

import ec.editer.msusuarios.dto.RolDTO;
import ec.editer.msusuarios.dto.UsuarioDTO;
import ec.editer.msusuarios.dto.UsuarioRolDTO;
import ec.editer.msusuarios.enums.RolEnum;
import ec.editer.msusuarios.model.User;
import ec.editer.msusuarios.model.UserRole;
import ec.editer.msusuarios.repository.RoleRepository;
import ec.editer.msusuarios.repository.UserRoleRepository;
import ec.editer.msusuarios.service.IRoleService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RoleService implements IRoleService{
    
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<UsuarioRolDTO> getAllByUserId(Integer userId) {
        var list = new ArrayList<UsuarioRolDTO>();
        userRoleRepository.findByUserId(userId).forEach(role -> {
            var roleDTO = new RolDTO();
            BeanUtils.copyProperties(role.getRol(), roleDTO);
            var userRoleDTO = new UsuarioRolDTO();
            BeanUtils.copyProperties(role, userRoleDTO);
            userRoleDTO.setRol(roleDTO);
            list.add(userRoleDTO);
        });
        return list;
    }

    @Override
    public UsuarioRolDTO create(RolEnum rolEnum, Integer userId) {
        var role = roleRepository.findByRolEnum(rolEnum); 
        log.info("***** role: {} *****", role.get().getId());
        var user = new User();
        user.setId(userId);
        var userRol = new UserRole();
        userRol.setRol(role.get());
        userRol.setUser(user);
        userRoleRepository.save(userRol);
               
        var roleDTO = new RolDTO();
        BeanUtils.copyProperties(role.get(), roleDTO);
        var userRoleDTO = new UsuarioRolDTO();
        BeanUtils.copyProperties(userRol, userRoleDTO);
        userRoleDTO.setRol(roleDTO);
        var userDTO = new UsuarioDTO();
        userDTO.setId(userId);
        userRoleDTO.setUsuario(userDTO);
        return userRoleDTO;
    }

    @Override
    public void delete(Integer id) {
        userRoleRepository.deleteById(id);
    }
    
}
