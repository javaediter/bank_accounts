/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author Edison Teran
 */
public class ValueSourceParameterizedTest {
    
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"Hello", "World!"})
    public void parameterizedTest(String source){
        System.out.println("argument: " + source);
        assertNotNull(source);
    }
}
