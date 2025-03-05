/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.repository;

import ec.editer.msreportes.model.Reporte;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Edison Teran
 */
public interface ReporteRepository extends MongoRepository<Reporte, String>{
    List<Reporte> findAllByClienteIdOrderByFechaDesc(Integer clienteId);
    Page<Reporte> findAllByClienteId(Integer clienteId, Pageable pageable);
}
