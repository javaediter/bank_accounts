/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.repository;

import ec.editer.msusuarios.enums.RolEnum;
import ec.editer.msusuarios.model.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface RoleRepository extends CrudRepository<Role, Integer>{
    Optional<Role> findByRolEnum(RolEnum rolEnum);
}
