/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.commons.clientes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Edison Teran
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteDataParcial {
    private Integer clienteId;
    private String direccion;
    private String telefono;
    private Integer estadoId;
    private Integer edad;
    private String nuevaPassword;
}
