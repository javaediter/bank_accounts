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
import org.springframework.data.jpa.repository.query.Procedure;

/**
 *
 * @author Edison Teran
 */
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{
    List<Movimiento> findAllByCuentaClienteIdAndFechaBetween(Integer clienteId, Date fechaStart, Date fechaEnd);
    List<Movimiento> findAllByCuentaCuentaId(Integer cuentaId, Sort sort);
 
    //En caso de que cambie el nombre del SP, no hay refactorizar el codigo
    //Los parametros deben estar en el mismo orden que en el SP
    //Internamente hace la llamada CALL
    @Procedure("sp_reporte")
    List<Object> reporteSP(java.sql.Date fechaInicio, java.sql.Date fechaFin, Integer clienteId);
    
    //El nombre del método es el mismo que el SP, no es necesario agregarlo en @Procedure
    //@Procedure
    //List<Object> sp_reporte(LocalDate fechaInicio, LocalDate fechaFin, Integer clienteId);
}
