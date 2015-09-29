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
import justiciagratuita.modelo.TasuntoDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class TasuntoDao extends BaseDao {

    public TasuntoDTO getTasuntoBy(String descripcion) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, descripcion, idtipo "
                + " from TASUNTO"
                + " where DESCRIPCION = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setString(1, descripcion);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                TasuntoDTO objeto = new TasuntoDTO();
                objeto.setId(rs.getInt("ID"));
                objeto.setDescripcion(rs.getString("descripcion"));
                objeto.setIdTipo(rs.getString("IDTIPO"));
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
    
    public TasuntoDTO getTasuntoBy(int id) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, descripcion, idtipo "
                + " from TASUNTO"
                + " where ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, id);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                TasuntoDTO objeto = new TasuntoDTO();
                objeto.setId(rs.getInt("ID"));
                objeto.setDescripcion(rs.getString("descripcion"));
                objeto.setIdTipo(rs.getString("IDTIPO"));
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
    
    public List<TasuntoDTO> ListaTasunto() {

        ResultSet rs = null;
        Statement statemt = null;
        String checkStr = "select ID, descripcion, idtipo "
                + " from TASUNTO"
                + " where id = ? ";
        
        List<TasuntoDTO> lista = new ArrayList();

        try {
            globalConnection = super.openDBConnection();
            statemt = globalConnection.createStatement();

            rs = statemt.executeQuery(checkStr);

            while (rs.next()) {
                TasuntoDTO objeto = new TasuntoDTO();
                objeto.setId(rs.getInt("id"));
                objeto.setDescripcion(rs.getString("descripcion"));
                objeto.setIdTipo(rs.getString("idtipo"));
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
