/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao;

/**
 *
 * @author joseluis.bachiller
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.jdbc.JdbcSQLException;


public class BaseDao {
    
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION_PROT = "jdbc:h2:";
    private static final String DB_CONNECTION_URL = "..\\JusticiaGratuitaDB\\JusticiaGratuitaDB";
    private static final String DB_CONNECTION_PAR = ";IFEXISTS=TRUE";
    private static final String DB_USER =     "justiciagr";
    private static final String DB_PASSWORD = "justiciagr";
   
    /*
        Si CONEXIONUNICA sólo abrimos la conexión una vez, al inicial el programa y 
        la cerramos cuando se cierre el programa
    */
    private static boolean CONEXIONUNICA = true;  // hay que mirarlo 
    private static boolean DB_IS_OK = true;

    protected Connection globalConnection; // Para que sea global no puede estar aquí.
    
   
    protected Connection openDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (globalConnection != null && !globalConnection.isClosed()) {
                return globalConnection;
            }
            dbConnection = DriverManager.getConnection(DB_CONNECTION_PROT+DB_CONNECTION_URL+DB_CONNECTION_PAR, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (JdbcSQLException ex) {
            DB_IS_OK = false;
            System.out.println(ex.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
 /*   protected void conecta () {
        globalConnection = openDBConnection();
    }
   */ 
    protected void closeDBConnection(Connection dbConnection) {

        try {
            if (!CONEXIONUNICA) {
                if (dbConnection != null && !dbConnection.isClosed()) {
                    dbConnection.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    /**
     * Indica si se va a abrir la conexión con la BBDD cada vez que se utilice o sólo 
     * una vez al iniciar el programa
     */
    public void closeConexionUnica() {
        CONEXIONUNICA = true;
        closeDBConnection(globalConnection);
    }
    
    /**
     * Para base de datos Embebidas (CONEXIONUNICA = true) abre la base de datos y
     * si no hay problema devuelve true.
     * @return si la bd es accesible y no está abierta por otro usuario
     */
    public boolean databaseIsOK() {
        if (CONEXIONUNICA) {
            globalConnection = openDBConnection();
            return DB_IS_OK;
        }
        return true;
    }
    
}
