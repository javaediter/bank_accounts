/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes;

import ec.editer.msreportes.model.Reporte;
import ec.editer.msreportes.repository.ReporteRepository;
import ec.editer.msreportes.service.ReporteService;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 *
 * @author Edison Teran
 */
@SpringBootTest(classes = {ReporteService.class})
public class SpringReporteServiceTest {
    
    @MockitoBean
    ReporteRepository reporteRepository;
    
    @Autowired
    ReporteService reporteService;
    
    @DisplayName("Obtener Ultimo Reporte con SpringBootTest")
    @Test
    public void testObtenerUltimoReporte(){
        Reporte reporte = Reporte.builder().clienteId(1).id("abc001").jsonContenido("\\{\\}").build();
        when(reporteRepository.findFirstByClienteIdOrderByFechaDesc(anyInt())).thenReturn(reporte);
        
        Optional<Reporte> opt = reporteService.obtenerUltimoReporte(1);
        
        assertFalse(opt.isEmpty());
        verify(reporteRepository).findFirstByClienteIdOrderByFechaDesc(anyInt());
    }
}
