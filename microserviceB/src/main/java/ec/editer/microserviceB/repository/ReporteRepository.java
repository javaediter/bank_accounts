/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.repository;

import ec.editer.microserviceB.model.Movimiento;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Edison Teran
 */
public interface ReporteRepository extends JpaRepository<Movimiento, Integer>{
    @Query(value = "CALL sp_reporte(:fechaInicio, :fechaFin, :clienteId);", nativeQuery = true)
    public List<Object> construirReporte(@Param("clienteId") Integer clienteId, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
