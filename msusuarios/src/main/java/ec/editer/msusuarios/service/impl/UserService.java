/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.service.impl;

import ec.editer.msusuarios.dto.UsuarioDTO;
import ec.editer.msusuarios.model.User;
import ec.editer.msusuarios.repository.UserRepository;
import ec.editer.msusuarios.service.IUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edison Teran
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements IUserService{
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository usuarioRepository;

    @Override
    public Optional<UsuarioDTO> getUserById(Integer id) {
        var user = usuarioRepository.findById(id);
        if(user.isPresent()){
            var dto = new UsuarioDTO();
            BeanUtils.copyProperties(user.get(), dto);
            return Optional.of(dto);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<UsuarioDTO> getAll() {
        var list = new ArrayList<UsuarioDTO>();
        usuarioRepository.findAll().forEach(user -> {
            var dto = new UsuarioDTO();
            BeanUtils.copyProperties(user, dto);
            list.add(dto);
        });
        return list;
    }

    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        final String temporal = "temporal123";
        var user = new User();
        BeanUtils.copyProperties(usuarioDTO, user);        
        user.setPassword(passwordEncoder.encode(temporal));
        usuarioRepository.save(user);
        usuarioDTO.setId(user.getId());
        return usuarioDTO;
    }

    @Override
    public UsuarioDTO update(UsuarioDTO usuarioDTO) {
        var user = usuarioRepository.findById(usuarioDTO.getId()).get();
        user.setActive(usuarioDTO.isActive());
        usuarioRepository.save(user);
        return usuarioDTO;
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
    
    @Override
    public UsuarioDTO updatePassword(String username, String newPassword) {
        var user = usuarioRepository.findByUsername(username).get();
        user.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(user);
        var dto = new UsuarioDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
