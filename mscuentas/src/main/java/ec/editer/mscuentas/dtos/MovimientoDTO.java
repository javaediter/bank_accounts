/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class MovimientoDTO {
    @NotNull
    private Integer movimientoId;
    
    @NotNull(message = "Fecha es requerido")
    private Date fecha;
    
    @NotNull(message = "Tipo Movimiento es requerido")
    @NotBlank(message = "Tipo Movimiento es requerido")
    private String tipoMovimiento;
    
    @NotNull(message = "Valor es requerido")
    private BigDecimal valor;
    
    @NotNull
    private BigDecimal saldo;
    
    @NotNull(message = "Cuenta es requerido")
    private CuentaDTO cuenta;
}
