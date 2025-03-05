/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msclientes.reporte;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Edison Teran
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reporte implements Serializable{
    private Integer clienteId;
    private List<Registro> registros;
}
