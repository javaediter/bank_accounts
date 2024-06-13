/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.dtos;

import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class ClienteDataParcial {
    private Integer clienteId;
    private String direccion;
    private String telefono;
    private Integer estadoId;
    private Integer edad;
    private String nuevaPassword;
}
