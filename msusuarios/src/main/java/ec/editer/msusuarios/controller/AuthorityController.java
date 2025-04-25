/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.controller;

import ec.editer.msusuarios.enums.RolEnum;
import ec.editer.msusuarios.service.IRoleService;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/roles")
public class AuthorityController {
    
    private final IRoleService roleService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Map<String, String> role){
        log.info("... save ...");
        return ResponseEntity.ok(roleService.create(RolEnum.valueOf(role.get("name").toUpperCase()), Integer.valueOf(role.get("userId"))));
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity allByUserId(@PathVariable(name = "id") Integer userId){
        log.info("... all by userId : {} ...", userId);
        return ResponseEntity.ok(roleService.getAllByUserId(userId));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        log.info("... delete: {} ...", id);
        roleService.delete(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(Collections.singletonMap("msg", "Record deleted success"));
    }
}
