/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao.triggers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joseluis.bachiller
 */
public class CreaTriggers {
    
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION_PROT = "jdbc:h2:";
    private static final String DB_CONNECTION_URL = "..\\JusticiaGratuitaDB\\JusticiaGratuitaDB";
    private static final String DB_CONNECTION_PAR = ";IFEXISTS=TRUE";
    private static final String DB_USER =     "justiciagr";
    private static final String DB_PASSWORD = "justiciagr";
    
    public static void main(String... args) throws Exception {
        Connection conn = null;
        Statement stat = null;
        try {
            Class.forName("org.h2.Driver");

            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_CONNECTION_PROT + DB_CONNECTION_URL + DB_CONNECTION_PAR, DB_USER, DB_PASSWORD);
            stat = conn.createStatement();
            /* Usuario */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla Usuario");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_USUARIO BEFORE UPDATE "
                    + "ON USUARIO FOR EACH ROW "
                    + "CALL \""+Tg_FecModifica.class.getName()+"\" ");
            /* Usuario FIN */
            /* PERSONA */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla Persona");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_PERSONA BEFORE UPDATE "
                    + "ON PERSONA FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* PERSONA FIN */
            /* JUZGADO */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla JUZGADO");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_JUZGADO BEFORE UPDATE "
                    + "ON JUZGADO FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* JUZGADO FIN */
            /* ABOGADO */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla ABOGADO");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_ABOGADO BEFORE UPDATE "
                    + "ON ABOGADO FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* ABOGADO FIN */
            /* PROCURADOR */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla PROCURADOR");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_PROCURADOR BEFORE UPDATE "
                    + "ON PROCURADOR FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* PROCURADOR FIN */
            /* COMISION */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla COMISION");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_COMISION BEFORE UPDATE "
                    + "ON COMISION FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* COMISION FIN */
            /* EXPEDIENTE */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla EXPEDIENTE");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_EXPEDIENTE BEFORE UPDATE "
                    + "ON EXPEDIENTE FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* EXPEDIENTE FIN */
            
            /* tablas auxiliares */
            /* ESTADOEXP */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla ESTADOEXP");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_ESTADOEXP BEFORE UPDATE "
                    + "ON ESTADOEXP FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* ESTADOEXP FIN */
            /* TRANSESTADOEXP */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers Tabla TRANSESTADOEXP");
            stat.execute("CREATE TRIGGER IF NOT EXISTS BU_TRANSESTADOEXP BEFORE UPDATE "
                    + "ON TRANSESTADOEXP FOR EACH ROW "
                    + "CALL \"justiciagratuita.dao.triggers.Tg_FecModifica\" ");
            /* TRANSESTADOEXP FIN */

            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Triggers creados");
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Lanzando Pruebas");
            testTriggers(conn);
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Lanzando Pruebas - Fin");
            stat.close();
            conn.close();
        } catch (Exception ex) {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }
     
    private static void testTriggers(Connection conn) throws Exception {
        Statement stat = null;
        try {
            stat = conn.createStatement();
            /* USUARIO */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_USUARIO");
            stat.execute("UPDATE USUARIO SET IDUSUARIO = 'bachi' WHERE IDPERSONA=1");
            
            /* PERSONA */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_PERSONA");
            stat.execute("UPDATE PERSONA SET PAPELLIDO = PAPELLIDO WHERE ID=1");
            
            /* JUZGADO */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_JUZGADO");
            stat.execute("UPDATE JUZGADO SET DESCRIPCION = DESCRIPCION WHERE ID=1");
            
            /* ABOGADO */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_ABOGADO");
            stat.execute("UPDATE ABOGADO SET IDPERSONA = IDPERSONA WHERE IDPERSONA=1");
            
            /* PROCURADOR */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_PROCURADOR");
            stat.execute("UPDATE PROCURADOR SET IDPERSONA = IDPERSONA WHERE IDPERSONA=1");
            
            /* COMISION */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_COMISION");
            stat.execute("UPDATE COMISION SET ID = ID WHERE ID=1");
            
            /* EXPEDIENTE */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_EXPEDIENTE");
            stat.execute("UPDATE EXPEDIENTE SET ID = ID WHERE ID=1");
            
            /* ESTADOEXP */
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.INFO, "Probando BU_ESTADOEXP");
            stat.execute("UPDATE ESTADOEXP SET ID = ID WHERE ID=1");

            stat.close();
        } catch (Exception ex) {
            if (stat != null) {
                stat.close();
            }
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }

    }
}
