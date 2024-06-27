/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.model;

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
import lombok.ToString;

/**
 *
 * @author Edison Teran
 */
@ToString
@Data
@Entity
@Table(name = "clientes", uniqueConstraints = {
    @UniqueConstraint(columnNames = "identificacion")
})
public class Cliente extends Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer clienteId;
    
    private String password;
    
    @JoinColumn(name = "estado_cliente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoCliente estadoCliente;
   
}
