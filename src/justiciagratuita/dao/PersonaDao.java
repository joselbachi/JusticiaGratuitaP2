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
    
    public PersonaDTO getPersonaByID(int id) {

        PersonaDTO objeto;
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, " +
                                " IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, " +
                                " TELEFONO, TELMOVIL, FECNAC, FECALTA, FECMODIFICA "
                + " from PERSONA where "
                + "ID = ? ";
        
        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, id);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                objeto = new PersonaDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("papellido"), rs.getString("sapellido"), rs.getString("idtipoidentificador"), rs.getString("identificador"));
                objeto.setDireccion(rs.getString("direccion"));
                objeto.setCodigoPostal(rs.getInt("codpostal"));
                objeto.setLocalidad(rs.getString("localidad"));
                objeto.setProvincia(rs.getString("provincia"));
                objeto.setTelefono(rs.getString("telefono"));
                objeto.setMovil(rs.getString("telmovil"));
                objeto.setFecNac(util.DateUtil.convertToEntityAttribute(rs.getDate("fecnac")));
                objeto.setFecAlta(util.DateUtil.convertToEntityAttribute(rs.getTimestamp("FECALTA")));
                objeto.setFecModifica(util.DateUtil.convertToEntityAttribute(rs.getTimestamp("FECMODIFICA")));
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
     * Recupera una persona de la tabla persona identificada por su documento
     * @param documento
     * @return 
     */
    public PersonaDTO getPersonaByDocu(String tipoDoc, String documento) {

        PersonaDTO objeto;
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, NOMBRE, PAPELLIDO, SAPELLIDO, IDTIPOIDENTIFICADOR, " +
                                " IDENTIFICADOR, DIRECCION, CODPOSTAL, LOCALIDAD, PROVINCIA, " +
                                " TELEFONO, TELMOVIL, FECNAC, FECALTA, FECMODIFICA "
                + " from PERSONA "
                + " where IDTIPOIDENTIFICADOR = ? "
                + " and  IDENTIFICADOR = ?";
        
        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setString(1, tipoDoc);
            ppStatemt.setString(2, documento);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                objeto = new PersonaDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("papellido"), rs.getString("sapellido"), rs.getString("idtipoidentificador"), rs.getString("identificador"));
                objeto.setDireccion(rs.getString("direccion"));
                objeto.setCodigoPostal(rs.getInt("codpostal"));
                objeto.setLocalidad(rs.getString("localidad"));
                objeto.setProvincia(rs.getString("provincia"));
                objeto.setTelefono(rs.getString("telefono"));
                objeto.setMovil(rs.getString("telmovil"));
                objeto.setFecNac(util.DateUtil.convertToEntityAttribute(rs.getDate("fecnac")));
                objeto.setFecAlta(util.DateUtil.convertToEntityAttribute(rs.getTimestamp("FECALTA")));
                objeto.setFecModifica(util.DateUtil.convertToEntityAttribute(rs.getTimestamp("FECMODIFICA")));
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
    
}
