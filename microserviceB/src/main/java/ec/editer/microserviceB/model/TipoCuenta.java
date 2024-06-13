/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
@Entity
@Table(name = "tipo_cuentas")
public class TipoCuenta implements Serializable{
    
    @Id
    @Column(name = "tipo_cuenta_id")
    private Integer tipoCuentaId;
    
    @Column(name = "nombre")
    private String nombre;
}
