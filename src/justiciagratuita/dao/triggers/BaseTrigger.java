/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao.triggers;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.api.Trigger;

/**
 *
 * @author joseluis.bachiller
 */
public class BaseTrigger implements Trigger {
    
    protected String TABLA;

    @Override
    public void init(Connection conn, String schemaName,
                String triggerName, String tableName, boolean before, int type) {
            // initialize the trigger object is necessary
        TABLA = tableName;
    }

    @Override
    public void fire(Connection cnctn, Object[] os, Object[] os1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
