/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.mscuentas;

import ec.editer.mscuentas.dtos.CuentaDTO;
import ec.editer.mscuentas.model.Cuenta;
import ec.editer.mscuentas.repository.CuentaRepository;
import ec.editer.mscuentas.service.impl.CuentaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Edison Teran
 */
@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {
        
    @Mock
    private CuentaRepository cuentaRepository;
    
    @Mock
    private RestTemplate restTemplate;
    
    @InjectMocks
    private CuentaService cuentaService;
    
    @DisplayName("Cuentas Por Cliente")
    @Test
    public void testCuentasPorCliente(){
        List<Cuenta> cuentas = new ArrayList<>();
        Cuenta ahorros = new Cuenta();
        ahorros.setClienteId(1);
        ahorros.setCuentaId(1);
        ahorros.setEstado(true);
        ahorros.setNumero("001");
        ahorros.setTipoCuenta("Ahorros");
        ahorros.setSaldoInicial(new BigDecimal(100));
        cuentas.add(ahorros);
        
        Cuenta corriente = new Cuenta();
        corriente.setClienteId(1);
        corriente.setCuentaId(2);
        corriente.setEstado(true);
        corriente.setNumero("901");
        corriente.setTipoCuenta("Corriente");
        corriente.setSaldoInicial(new BigDecimal(100));
        
        String nombreCliente = "Guest Guest";
        
        when(cuentaRepository.findAllByClienteId(1)).thenReturn(cuentas);
        when(restTemplate.getForObject(null + "/nombre-cliente?clienteId=" + 1, String.class)).thenReturn(nombreCliente);
        
        List<CuentaDTO> dtos = cuentaService.cuentasPorCliente(1);
        
        assertNotNull(dtos);
        dtos.forEach(x -> {
            assertEquals(x.getNombreCliente(), nombreCliente);
        });
        verify(cuentaRepository).findAllByClienteId(anyInt());
    }
    
    @DisplayName("Obtener Clientes Ids")
    @Test
    public void testObtenerClientesIds(){
        List<Integer> ids = Arrays.asList(1,2,3);
        when(cuentaRepository.obtenerClientesIds()).thenReturn(ids);
        
        List<Integer> idsClientes = cuentaService.obtenerClientesIds();
        assertEquals(ids, idsClientes);
        verify(cuentaRepository).obtenerClientesIds();
    }
}
