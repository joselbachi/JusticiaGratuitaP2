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
import justiciagratuita.modelo.PersonaDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class PersonaDao extends BaseDao {
    
    public PersonaDTO getUsuarioByID(int id) {

        PersonaDTO usuario;
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
                usuario = new PersonaDTO(id, rs.getString("nombre"), rs.getString("papellido"), rs.getString("sapellido"), rs.getString("idtipoidentificador"), rs.getString("identificador"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setCodigoPostal(rs.getInt("codpostal"));
                usuario.setLocalidad(rs.getString("localidad"));
                usuario.setProvincia(rs.getString("provincia"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setMovil(rs.getString("telmovil"));
                return usuario;
            }
        } catch (DatabaseInUseException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } catch (NullPointerException nex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, nex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
}
