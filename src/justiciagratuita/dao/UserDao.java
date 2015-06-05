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

import justiciagratuita.modelo.Usuario;

/**
 *
 * @author joseluis.bachiller
 */
public class UserDao extends BaseDao {

    /**
     * Comprueba sie usu/contr tiene acceso a la aplicación
     * @param usu
     * @param contra
     * @return devuelve los datos del usuario en caso de que conincidan usuario y contraseña
     */
    public Usuario checkUser(String usu, String contra) {

        Usuario usuario;
        ResultSet rs = null;
        PreparedStatement checkUser = null;
        String checkStr = "select id, perfil from user where "
                + "USUARIO = ? AND PASSWD = ?";
        //String checkStr = " select table_name from tables";
        
        try {
            globalConnection = super.openDBConnection();
            checkUser = globalConnection.prepareStatement(checkStr);
            checkUser.setString(1, usu);
            checkUser.setString(2, contra);

            rs = checkUser.executeQuery();

            if (rs.next()) {
                usuario = Usuario.of(Integer.toString(rs.getInt("id")));
                usuario.setNombre(usu);
                usuario.setPerfil(rs.getInt("perfil"));
                return usuario;
            }
        } catch (NullPointerException nex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, nex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
}
