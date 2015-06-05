/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.exceptions;

/**
 *
 * @author joseluis.bachiller
 */
public class DatabaseInUseException extends Exception {
    public DatabaseInUseException(String database) {
        super("La base de datos " +  database + " ya está abierta. Hay otra persona usándola. ");
    }
}
