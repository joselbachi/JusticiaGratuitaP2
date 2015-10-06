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

import justiciagratuita.modelo.TdocumentoDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class TdocumentoDao extends BaseDao {

    
 /*   public TdocumentoDTO getTdocumentoByDesc(String descripcion) {
        TdocumentoDTO objeto = new TdocumentoDTO(descripcion);
        return objeto;
    }
*/    
    public TdocumentoDTO getTdocumentoById(String id) {
        
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select * "
                + " from TTIDENTIFICA"
                + " where ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setString(1, id);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                TdocumentoDTO objeto = new TdocumentoDTO(rs.getString("ID"), rs.getString("DESCRIPCION"));
                return objeto;
            }
        } catch (DatabaseInUseException | NullPointerException | SQLException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ppStatemt != null && !ppStatemt.isClosed() ) {
                    ppStatemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
    /**
     * Devuelve la lista de los tipos de documentos identificativos tabla TTIDENTIFICA
     * @return lista (cadenas) tipos de documento
     */
    public List<TdocumentoDTO> listaTiposDocumento() {

        ResultSet rs = null;
        List<TdocumentoDTO> lista = new ArrayList();
        Statement statemt = null;
        String checkStr = "select * "
                + " from TTIDENTIFICA";

        try {
            globalConnection = super.openDBConnection();
            statemt = globalConnection.createStatement();

            rs = statemt.executeQuery(checkStr);

            while (rs.next()) {
                TdocumentoDTO objeto = new TdocumentoDTO(rs.getString("id"),rs.getString("descripcion"));
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
                if (statemt != null && !statemt.isClosed() ) {
                    statemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
    /**
     * Devuelve el código identificador del tipo de documento correspondiente a la descripción
     * @param descripcion
     * @return 
     */
    public String getTipoDocumentoByStr(String descripcion) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select * "
                + " from TTIDENTIFICA"
                + " where DESCRIPCION = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setString(1, descripcion);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                return rs.getString("ID");
            }
        } catch (DatabaseInUseException | NullPointerException | SQLException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ppStatemt != null && !ppStatemt.isClosed() ) {
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
