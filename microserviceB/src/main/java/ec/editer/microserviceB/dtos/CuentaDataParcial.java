/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceB.dtos;

import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class CuentaDataParcial {
    private String numeroCuenta;
    private Integer tipoCuentaId;
    private Integer estadoCuentaId;
}
