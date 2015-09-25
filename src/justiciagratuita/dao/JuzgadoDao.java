/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import justiciagratuita.exceptions.DatabaseInUseException;
import justiciagratuita.modelo.JuzgadoDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class JuzgadoDao extends BaseDao {

    public JuzgadoDTO getJuzgadoBy(String descripcion) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, DESCRIPCION, IDTIPO, FECALTA, FECMODIFICA "
                + " from JUZGADO"
                + " where DESCRIPCION = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setString(1, descripcion);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                JuzgadoDTO objeto = new JuzgadoDTO();
                objeto.setId(rs.getInt("id"));
                objeto.setDescripcion(rs.getString("descripcion"));
                objeto.setTipo(null);
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

    public JuzgadoDTO getJuzgadoByStr(int id) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, DESCRIPCION, IDTIPO, FECALTA, FECMODIFICA "
                + " from JUZGADO"
                + " where ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, id);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                JuzgadoDTO objeto = new JuzgadoDTO();
                objeto.setId(rs.getInt("id"));
                objeto.setDescripcion(rs.getString("descripcion"));
                objeto.setTipo(null);
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

    public List<JuzgadoDTO> ListaJuzgados(int id) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, DESCRIPCION, IDTIPO, FECALTA, FECMODIFICA "
                + " from JUZGADO"
                + " where ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, id);

            rs = ppStatemt.executeQuery();
            
            List<JuzgadoDTO> lista = new ArrayList();

            while (rs.next()) {
                JuzgadoDTO objeto = new JuzgadoDTO();
                objeto.setId(rs.getInt("id"));
                objeto.setDescripcion(rs.getString("descripcion"));
                objeto.setTipo(null);
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
}
