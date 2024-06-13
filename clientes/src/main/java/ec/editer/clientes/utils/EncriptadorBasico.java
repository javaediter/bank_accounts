/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.editer.clientes.utils;

/**
 *
 * @author Edison Teran
 */
public class EncriptadorBasico {
    public String encriptador(String texto) {
        char[] key = {'a', 'Z', 'Y', 'x', '-', '.'};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == '0') {
                sb.append(key[4]);
            } else if ((i % 2) == 0) {
                sb.append(key[0]);
                sb.append(key[2]);
            } else {
                sb.append(key[1]);
                sb.append(key[3]);
            }
        }
        sb.append(key[5]);
        return sb.toString();
    }
}
