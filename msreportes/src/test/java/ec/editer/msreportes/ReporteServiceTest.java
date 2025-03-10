/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.msreportes;

import ec.editer.msreportes.model.Reporte;
import ec.editer.msreportes.repository.ReporteRepository;
import ec.editer.msreportes.service.ReporteService;
import java.util.ArrayList;
import java.util.List;
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
        List<Reporte> reportes = new ArrayList<>();
        when(reporteRepository.findAllByClienteIdOrderByFechaDesc(anyInt())).thenReturn(reportes);
        
        Optional<Reporte> opt = reporteService.obtenerUltimoReporte(1);
        
        assertTrue(opt.isEmpty());
        verify(reporteRepository).findAllByClienteIdOrderByFechaDesc(anyInt());
    }
}
