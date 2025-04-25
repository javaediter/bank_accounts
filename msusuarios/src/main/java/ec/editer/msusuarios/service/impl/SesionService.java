/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.service.impl;

import ec.editer.msusuarios.dto.TokenDTO;
import ec.editer.msusuarios.model.Sesion;
import ec.editer.msusuarios.model.User;
import ec.editer.msusuarios.repository.SesionRepository;
import ec.editer.msusuarios.service.ISesionService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ec.editer.msusuarios.repository.UserRepository;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SesionService implements ISesionService{
    
    @Value("${amqp.token.topic.name}") 
    private String topicExchangeName;
    
    private final UserRepository usuarioRepository;    
    private final SesionRepository sesionRepository;    
    private final PasswordEncoder passwordEncoder;    
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Optional<TokenDTO> autenticarUsuario(String username, String newPassword) {
        Optional<User> opt = usuarioRepository.findByUsername(username);
        if(opt.isPresent()){
            Date fecha = new Date();
            StringBuilder token = new StringBuilder();
            token.append(fecha.getTime());
            token.append(";");
            token.append(username);
            token.append(";");
            token.append(newPassword);
            token.append(";");
            
            User user = opt.get();
            
            user.getRoles().forEach(x -> {
                token.append(x.getRol().getRolEnum().name());
                token.append(";");
            });
                    
            Sesion sesion = new Sesion();
            sesion.setActiva(true);
            sesion.setUsuarioId(user.getId());
            sesion.setFecha(fecha);
            sesion.setToken(passwordEncoder.encode(token.toString()));
            sesionRepository.save(sesion);
            
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(sesion.getToken());
            
            publicarToken(tokenDTO);
            
            return Optional.of(tokenDTO);
        }else{
            return Optional.empty();
        }
    }

    private void publicarToken(TokenDTO tokenDTO) {
        log.info("...publicando mensaje tokenDTO...");
        final String routingKey = "usuarios.token.tkn";
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, tokenDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("... loadUserByUsername for {} ...", username);
        return usuarioRepository.findByUsername(username).map(user -> {
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            user.getRoles().forEach(userRol -> {
                    log.info("***** {} *****", userRol.getRol().getRolEnum().name());
                    authorities
                            .add(new SimpleGrantedAuthority(userRol.getRol().getRolEnum().name()));
                });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User invalid " + username));
    }
    
}
