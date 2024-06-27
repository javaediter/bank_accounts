/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.commons.cuentas.dtos;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class MovimientoDTO {
    private Integer movimientoId;
    private Date fecha;
    private BigDecimal valor;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private CuentaDTO cuenta;
    private TipoMovimientoDTO tipoMovimiento;
}
