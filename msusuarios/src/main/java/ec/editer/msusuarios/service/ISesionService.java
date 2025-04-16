/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.service;

import ec.editer.msusuarios.dto.IngresoDTO;
import ec.editer.msusuarios.dto.TokenDTO;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Edison Teran
 */
public interface ISesionService extends UserDetailsService{
    public Optional<TokenDTO> autenticarUsuario(IngresoDTO ingresoDTO);
}
