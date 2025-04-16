/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.repository;

import ec.editer.msusuarios.model.Sesion;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Edison Teran
 */
public interface SesionRepository extends MongoRepository<Sesion, String>{
    public Sesion findFirstByUsuarioIdOrderByFechaDesc(Integer usuarioId);
}
