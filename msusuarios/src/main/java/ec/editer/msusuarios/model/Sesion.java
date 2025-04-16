/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msusuarios.model;

import jakarta.persistence.Id;
import java.util.Date;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Edison Teran
 */
@Data
@Document(collation = "sesiones")
public class Sesion {
    @Id
    private String id;
    private Integer usuarioId;
    private Date fecha;
    private String token;
    private boolean activa;
}
