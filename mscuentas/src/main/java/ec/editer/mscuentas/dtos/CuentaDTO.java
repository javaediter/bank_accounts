/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.editer.mscuentas.model.Movimiento;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class CuentaDTO {
    private Integer cuentaId;
    private Integer clienteId;
    private String nombreCliente;
    private String numero;
    private BigDecimal saldoInicial;
    private String tipoCuenta;
    private boolean estado;
    
    @JsonIgnore
    private List<MovimientoDTO> movimientos;
}
