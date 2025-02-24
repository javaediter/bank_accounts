/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class CuentaDTO {
    @NotNull
    private Integer cuentaId;
    
    @NotNull
    private Integer clienteId;
    
    @NotNull
    private String nombreCliente;
    
    @NotNull(message = "Numero es requerido")
    @NotBlank(message = "Numero es requerido")
    private String numero;
    
    @NotNull
    private BigDecimal saldoInicial;
    
    @NotNull(message = "Tipo Cuenta es requerido")
    @NotBlank(message = "Tipo Cuenta es requerido")
    private String tipoCuenta;
    
    @NotNull
    private boolean estado;
    
    @JsonIgnore
    private List<MovimientoDTO> movimientos;
}
