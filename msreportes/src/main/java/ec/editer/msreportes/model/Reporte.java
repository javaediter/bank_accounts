/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Edison Teran
 */
@Builder
@Data
@Document(collection = "reportes")
public class Reporte {
    
    @Id
    private String id;
    private Integer clienteId;
    private String jsonContenido;
    private Date fecha;
}
