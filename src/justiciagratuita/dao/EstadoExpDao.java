/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import justiciagratuita.exceptions.DatabaseInUseException;
import justiciagratuita.modelo.EstadoExpDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class EstadoExpDao extends BaseDao {
    
        public EstadoExpDTO getEstadoBy(String descripcion) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, descripcion, descrcorta "
                + " from ESTADOEXP"
                + " where DESCRIPCION = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setString(1, descripcion);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                EstadoExpDTO objeto = new EstadoExpDTO(rs.getInt("ID"), rs.getString("descripcion"), rs.getString("descrcorta"));
                return objeto;
            }
        } catch (DatabaseInUseException | NullPointerException | SQLException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ppStatemt != null && !ppStatemt.isClosed()) {
                    ppStatemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
    public EstadoExpDTO getEstadoBy(int id) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, descripcion, descrcorta "
                + " from ESTADOEXP"
                + " where ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, id);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                EstadoExpDTO objeto = new EstadoExpDTO(rs.getInt("ID"), rs.getString("descripcion"), rs.getString("descrcorta"));
                return objeto;
            }
        } catch (DatabaseInUseException | NullPointerException | SQLException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ppStatemt != null && !ppStatemt.isClosed()) {
                    ppStatemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
    public List<EstadoExpDTO> ListaEstado() {

        ResultSet rs = null;
        Statement statemt = null;
        String checkStr = "select ID, descripcion, descrcorta "
                + " from ESTADOEXP"
                + " where id = ? "
                + " and fecbaja not null";
        
        List<EstadoExpDTO> lista = new ArrayList();

        try {
            globalConnection = super.openDBConnection();
            statemt = globalConnection.createStatement();

            rs = statemt.executeQuery(checkStr);

            while (rs.next()) {
                EstadoExpDTO objeto = new EstadoExpDTO(rs.getInt("ID"), rs.getString("descripcion"), rs.getString("descrcorta"));
                objeto.setFecBaja(util.DateUtil.convertToEntityAttribute(rs.getTimestamp("fecbaja")));
                lista.add(objeto);
            }
            return lista;
        } catch (DatabaseInUseException | NullPointerException | SQLException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (statemt != null && !statemt.isClosed()) {
                    statemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
}
