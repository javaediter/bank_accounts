/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
@Entity
@Table(name = "cuentas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero", "tipo_cuenta"})
})
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Integer cuentaId;
    
    @Column(name = "cliente_id")
    private Integer clienteId;
    
    @Column(name = "numero")
    private String numero;
    
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;
    
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;
    
    private boolean estado;
    
}
