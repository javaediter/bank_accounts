/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas.reporte;

import java.util.List;
import lombok.Value;

/**
 *
 * @author Edison Teran
 */
@Value
public class Reporte {
    private List<Registro> registros;
}
