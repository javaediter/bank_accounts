/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.repository;

import ec.editer.mscuentas.model.Movimiento;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Edison Teran
 */
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{
    List<Movimiento> findAllByCuentaClienteIdAndFechaBetween(Integer clienteId, Date fechaStart, Date fechaEnd);
    List<Movimiento> findAllByCuentaCuentaId(Integer cuentaId, Sort sort);
    
    @Query(value = "CALL sp_reporte(:fechaInicio, :fechaFin, :clienteId);", nativeQuery = true)
    List<Object> construirReporte(@Param("clienteId") Integer clienteId, @Param("fechaInicio") java.sql.Date fechaInicio, @Param("fechaFin") java.sql.Date fechaFin);
}
