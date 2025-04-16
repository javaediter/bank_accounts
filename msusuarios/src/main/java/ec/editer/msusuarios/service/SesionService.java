/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.service;

import ec.editer.msusuarios.dto.IngresoDTO;
import ec.editer.msusuarios.dto.TokenDTO;
import ec.editer.msusuarios.model.Sesion;
import ec.editer.msusuarios.model.User;
import ec.editer.msusuarios.repository.SesionRepository;
import ec.editer.msusuarios.repository.UsuarioRepository;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Service
public class SesionService implements ISesionService{
    
    @Value("${amqp.token.topic.name}") 
    private String topicExchangeName;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SesionRepository sesionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Optional<TokenDTO> autenticarUsuario(IngresoDTO ingresoDTO) {
        Optional<User> opt = usuarioRepository.findByUsername(ingresoDTO.getUsername());
        if(opt.isPresent()){
            Date fecha = new Date();
            StringBuilder token = new StringBuilder();
            token.append(fecha.getTime());
            token.append(";");
            token.append(ingresoDTO.getUsername());
            token.append(";");
            token.append(ingresoDTO.getPassword());
            token.append(";");
            
            User user = opt.get();
            
            user.getRoles().forEach(x -> {
                token.append(x.getRol().getRolEnum().name());
                token.append(";");
            });
                    
            Sesion sesion = new Sesion();
            sesion.setId("");
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
        log.info("...publicando mensaje tokenDTO");
        final String routingKey = "usuarios.token.tkn";
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, tokenDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = usuarioRepository.findByUsername(username);
        if(opt.isPresent()){
            return opt.get();
        }else{
            throw new UsernameNotFoundException("Invalid user: " + username);
        }
    }
    
}
