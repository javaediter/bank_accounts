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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Edison Teran
 */
@SpringBootTest(classes = {CuentaService.class})
public class SpringCuentaServiceTest {
    
    @MockitoBean
    CuentaRepository cuentaRepository;
    
    @MockitoBean
    RestTemplate restTemplate;
    
    @Autowired
    CuentaService cuentaService;
    
    @DisplayName("Cuentas Por Cliente con SpringBootTest")
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
        cuentas.add(corriente);
        
        String nombreCliente = "Guest Guest";
        
        when(cuentaRepository.findAllByClienteId(1)).thenReturn(cuentas);
        when(restTemplate.getForObject("http://localhost:8081/api/clientes/nombre-cliente?clienteId=" + 1, String.class)).thenReturn(nombreCliente);
        
        List<CuentaDTO> dtos = cuentaService.cuentasPorCliente(1);
        
        assertNotNull(dtos);
        dtos.forEach(x -> {
            assertEquals(x.getNombreCliente(), nombreCliente);
        });
        verify(cuentaRepository).findAllByClienteId(anyInt());
    }
    
    @DisplayName("Obtener Clientes Ids con SpringBootTest")
    @Test
    public void testObtenerClientesIds(){
        List<Integer> ids = Arrays.asList(1,2,3);
        when(cuentaRepository.obtenerClientesIds()).thenReturn(ids);
        
        List<Integer> idsClientes = cuentaService.obtenerClientesIds();
        assertEquals(ids, idsClientes);
        verify(cuentaRepository).obtenerClientesIds();
    }
}
