/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.reporte;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class Registro {
    @JsonProperty("Fecha")
    private String fecha;
    
    @JsonProperty("Cliente")
    private String cliente;
    
    @JsonProperty("Numero Cuenta")
    private String numeroCuenta;
    
    @JsonProperty("Tipo")
    private String tipoCuenta;
    
    @JsonProperty("Saldo Inicial")
    private BigDecimal saldoInicial;
    
    @JsonProperty("Estado")
    private boolean estado;
    
    @JsonProperty("Movimiento")
    private BigDecimal movimiento;
    
    @JsonProperty("Saldo Disponible")
    private BigDecimal saldoDisponible;
}
