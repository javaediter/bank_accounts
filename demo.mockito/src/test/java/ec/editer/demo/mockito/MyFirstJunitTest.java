/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.demo.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Edison Teran
 */
public class MyFirstJunitTest {
    
    @Disabled
    @Test
    public void myFirstTest(){
        String msg = "1 + 1 is equalt to 2";
        System.out.println(msg);
        assertEquals(2, 1 + 1, msg);
    }
}
