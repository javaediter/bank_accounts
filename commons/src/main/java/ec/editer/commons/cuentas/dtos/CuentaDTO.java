/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.commons.cuentas.dtos;

import java.math.BigDecimal;
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
    private BigDecimal saldoDisponible;
    private TipoCuentaDTO tipoCuenta;
    private EstadoCuentaDTO estadoCuenta;
}
