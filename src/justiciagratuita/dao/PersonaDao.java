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
public class PersonaDao extends BaseDao {
    
    public Usuario getUsuarioByID(int id) {

        Usuario usuario;
        ResultSet rs = null;
        PreparedStatement checkUser = null;
        String checkStr = "select ID, NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, " +
                                " IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, " +
                                " TELEFONO, TELMOVIL "            
                + " from PERSONA where "
                + "ID = ? ";
        
        try {
            globalConnection = super.openDBConnection();
            checkUser = globalConnection.prepareStatement(checkStr);
            checkUser.setInt(1, id);

            rs = checkUser.executeQuery();

            if (rs.next()) {
                usuario = Usuario.of("");

                usuario.setNombre(rs.getString("nombre"));
                usuario.setPerfil(rs.getString("idperfil"));
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