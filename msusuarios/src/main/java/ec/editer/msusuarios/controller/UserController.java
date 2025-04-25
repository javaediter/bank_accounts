/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.controller;

import ec.editer.msusuarios.dto.UsuarioDTO;
import ec.editer.msusuarios.service.IUserService;
import jakarta.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {    
    
    private final IUserService userService;
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity find(@PathVariable Integer id){
        log.info("... find {} .. ", id);
        Optional<UsuarioDTO> opt = userService.getUserById(id);
        if(opt.isPresent()){
            return ResponseEntity.ok(opt.get());
        }else{
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(404))
                    .body(Collections.singletonMap("msg", "User not found"));
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/save")
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO dto){
        log.info("...save...");
        return ResponseEntity.ok(userService.create(dto));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update")
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO dto){
        log.info("... update {} ...", dto.getId());
        return ResponseEntity.ok(userService.update(dto));
    }
 
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public Map<String, String> delete(@PathParam("id") Integer userId){
        userService.delete(userId);
        return Collections.singletonMap("msg", "Record deleted success");
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(value = "/update-pwd")
    public ResponseEntity<UsuarioDTO> updatePassword(@RequestBody Map<String, String> credentials){
        return ResponseEntity.ok(userService.updatePassword(credentials.get("username"), credentials.get("pwd")));
    }
}
