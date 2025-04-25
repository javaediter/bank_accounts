/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.repository;

import ec.editer.msusuarios.model.UserRole;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edison Teran
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Integer>{
    List<UserRole> findByUserId(Integer userId);
}
