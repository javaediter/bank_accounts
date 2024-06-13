/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
@Entity
@Table(name = "cuentas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero", "tipo_cuenta_id"})
})
public class Cuenta implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Integer cuentaId;
    
    @Column(name = "cliente_id")
    private Integer clienteId;
    
    @Column(name = "numero")
    private String numero;
    
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;
    
    @Column(name = "saldo_disponible")
    private BigDecimal saldoDisponible;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_cuenta_id")
    private TipoCuenta tipoCuenta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_cuenta_id")
    private EstadoCuenta estadoCuenta;
}
