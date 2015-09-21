/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao.triggers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.api.Trigger;

/**
 *
 * @author joseluis.bachiller
 */
public class Tg_FecModifica extends BaseTrigger implements Trigger {

    /**
     * Initializes the trigger.
     *
     * @param conn a connection to the database
     * @param schemaName the name of the schema
     * @param triggerName the name of the trigger used in the CREATE TRIGGER
     * statement
     * @param tableName the name of the table
     * @param before whether the fire method is called before or after the
     * operation is performed
     * @param type the operation type: INSERT, UPDATE, or DELETE
     */
    @Override
    public void init(Connection conn, String schemaName,
            String triggerName, String tableName, boolean before, int type) {
        // initialize the trigger object is necessary
        super.init(conn, schemaName, triggerName, tableName, before, type);
    }

    /**
     * This method is called for each triggered action.
     *
     * @param conn a connection to the database
     * @param oldRow the old row, or null if no old row is available (for
     * INSERT)
     * @param newRow the new row, or null if no new row is available (for
     * DELETE)
     * @throws SQLException if the operation must be undone
     */
    @Override
    public void fire(Connection conn,
            Object[] oldRow, Object[] newRow)
            throws SQLException {
        if (newRow != null) {
            // FecModifica debe ser el último campo de la tabla que use este trigger.
            int posicion = indexOFFecModifica(conn);
            if (posicion >= 0) {
              newRow[posicion - 1] = util.DateUtil.convertToDatabaseColumn(LocalDateTime.now());   
            }
        }

    }

    @Override
    public void close() {
        // ignore
    }

    @Override
    public void remove() {
        // ignore
    }

    private int indexOFFecModifica(Connection conn) {
        Statement stat = null;
        PreparedStatement checkFecmod = null;
        ResultSet rs = null;
        int posicion = -1;

        String checkStr = "select ORDINAL_POSITION from INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_CATALOG = 'JUSTICIAGRATUITADB' "
                + "AND COLUMN_NAME = 'FECMODIFICA' "
                + "AND TABLE_NAME = ? ";

        try {

            checkFecmod = conn.prepareStatement(checkStr);
            checkFecmod.setString(1, TABLA);

            rs = checkFecmod.executeQuery();

            if (rs.next()) {
                posicion = rs.getInt("ORDINAL_POSITION");
            }
        } catch (Exception ex) {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(Tg_FecModifica.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            Logger.getLogger(CreaTriggers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stat != null && !stat.isClosed()) {
                    try {
                        stat.close();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Tg_FecModifica.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (checkFecmod != null && !checkFecmod.isClosed()) {
                    checkFecmod.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Tg_FecModifica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return posicion;
    }

}
