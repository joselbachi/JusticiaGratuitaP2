/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import justiciagratuita.exceptions.DatabaseInUseException;

import justiciagratuita.modelo.UsuarioDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class UsuarioDao extends BaseDao {

    /**
     * Comprueba sie usu/contr tiene acceso a la aplicación
     * @param usu
     * @param contra
     * @return devuelve los datos del usuario en caso de que conincidan usuario y contraseña
     */
    public UsuarioDTO checkUser(String usu, String contra) {

        UsuarioDTO usuario;
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select idpersona, idperfil from usuario where "
                + "IDUSUARIO = ? AND CONTRASENA = ?";
        //String checkStr = " select table_name from tables";
        
        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setString(1, usu);
            ppStatemt.setString(2, contra);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                usuario = UsuarioDTO.of(usu);
                //usuario.setNombre(usu);
                usuario.setPerfil(rs.getString("idperfil"));
                usuario.setIdPersona(rs.getInt("idpersona"));
                return usuario;
            }
        } catch (NullPointerException nex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, nex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseInUseException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void updateUser(UsuarioDTO user) {

        ResultSet rs = null;
        PreparedStatement updateUser = null;
        int result = 0;
        String checkStr = "update USUARIO set IDUSUARIO = ?, CONTRASENA = ?, IDPERFIL = ?, FECMODIFICA = ?  "
                + "where idpersona = ?";
        
        try {
            globalConnection = super.openDBConnection();
            updateUser = globalConnection.prepareStatement(checkStr);
            updateUser.setString(1, user.getUsuario());
            updateUser.setString(2, user.getPasswd());
            updateUser.setString(3, user.getPerfil());
            updateUser.setInt(4, user.getIdPersona());
            updateUser.setTimestamp(5, java.sql.Timestamp.from(java.time.Instant.now()));

            result = updateUser.executeUpdate();

            if (result != 1) {
                // no se ha actualizado nada
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseInUseException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (updateUser != null && !updateUser.isClosed() ) {
                    updateUser.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }

    }
}
