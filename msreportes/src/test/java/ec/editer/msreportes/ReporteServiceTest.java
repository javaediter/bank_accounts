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
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {
    
    @Mock
    private ReporteRepository reporteRepository;
    
    @InjectMocks
    private ReporteService reporteService;
    
    @DisplayName("Obtener Ultimo Reporte")
    @Test
    public void testObtenerUltimoReporte(){
        Reporte reporte = Reporte.builder().clienteId(1).id("abc001").jsonContenido("\\{\\}").build();
        when(reporteRepository.findFirstByClienteIdOrderByFechaDesc(anyInt())).thenReturn(reporte);
        
        Optional<Reporte> opt = reporteService.obtenerUltimoReporte(1);
        
        assertFalse(opt.isEmpty());
        verify(reporteRepository).findFirstByClienteIdOrderByFechaDesc(anyInt());
    }
}
