/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.microserviceA.dtos;

import lombok.Data;

/**
 *
 * @author Edison Teran
 */
@Data
public class ClienteDTO extends PersonaDTO{
    private Integer clienteId;
    private String password;
    private EstadoClienteDTO estadoCliente;
}