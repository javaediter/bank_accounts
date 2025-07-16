/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;

/**
 *
 * @author Edison Teran
 */
@DisplayName("A special test case")
public class DisplayNameTest {
    
    @Disabled
    //@Test
    @RepeatedTest(3) // el test se repite 3 veces
    @DisplayName("Simple test sum operation")
    @Tag("math")
    public void simpleTest(){
        String message = "1 + 1 should be equalt to 2";
        assertEquals(2, 1 + 1, message);
    }
}
