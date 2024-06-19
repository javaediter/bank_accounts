/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Edison Teran
 */
@ToString
@Data
@Entity
@Table(name = "estado_clientes")
public class EstadoCliente implements Serializable{
    
    @Id
    @Column(name = "estado_cliente_id")
    private Integer estadoClienteId;
    private boolean valor;
}
