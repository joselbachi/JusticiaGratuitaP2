/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author joseluis.bachiller
 */
public class StringUtil {
    
    /**
     * Limpia la cadena de espacios por delante y por detrás
     * @param cadena 
     * @return cadena sin espacios
     */
    public static String limpiaCadena(String cadena) {
        if (cadena != null) {
            return cadena.trim();
        }
        return null;
    }

}
