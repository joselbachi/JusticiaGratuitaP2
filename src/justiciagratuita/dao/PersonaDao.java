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
import java.util.logging.Level;
import java.util.logging.Logger;
import justiciagratuita.exceptions.DatabaseInUseException;
import justiciagratuita.modelo.PersonaDTO;
import util.ValidationsUtil;

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
            
            TdocumentoDao tdocu = new TdocumentoDao();

            if (rs.next()) {
                objeto = new PersonaDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("papellido"), rs.getString("sapellido"), tdocu.getTdocumentoById(rs.getString("idtipoidentificador")), rs.getString("identificador"));
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
            
            TdocumentoDao tdocu = new TdocumentoDao();

            if (rs.next()) {
                objeto = new PersonaDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("papellido"), rs.getString("sapellido"), tdocu.getTdocumentoById(rs.getString("idtipoidentificador")), rs.getString("identificador"));
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
     * Guarda los datos de una persona nueva
     * @param persona que va a a guardarse
     * @return código ID único identificador de la persona, -1 en caso de error
     */
    public int guardaNewPersona(PersonaDTO persona) {
        Statement statemt = null;
        int idPerson = getSiguienteId();
        String insrtStr = "Insert into PERSONA (IDTIPOIDENTIFICADOR," +
                          "IDENTIFICADOR, NOMBRE, PAPELLIDO, ";
        if (!ValidationsUtil.isCadenaVacia(persona.getsApellido())) {
            insrtStr = insrtStr + "   SAPELLIDO, ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getDireccion())) {
            insrtStr = insrtStr + "   DIRECCION, ";
        }
        if (persona.getCodigoPostal() > 0) {
            insrtStr = insrtStr + "   CODPOSTAL, ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getLocalidad())) {
            insrtStr = insrtStr + "   LOCALIDAD, ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getProvincia())) {
            insrtStr = insrtStr + "   PROVINCIA, ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getTelefono())) {
            insrtStr = insrtStr + "   TELEFONO, ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getMovil())) {
            insrtStr = insrtStr + "   TELMOVIL, ";
        }
        if (persona.getFecNac() != null) {
            insrtStr = insrtStr + "   FECNAC, ";
        }
        insrtStr = insrtStr
                + "   ID) "
                + " values (" 
                + "'" + persona.getTipoIdentificador().getId()  + "', " 
                + "'" + persona.getIdentificador() + "', " 
                + "'" + persona.getNombre() + "', " 
                + "'" + persona.getpApellido() + "', ";
        if (!ValidationsUtil.isCadenaVacia(persona.getsApellido())) {
            insrtStr = insrtStr + "'" + persona.getsApellido() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getDireccion())) {
            insrtStr = insrtStr + "'" + persona.getDireccion() + "', ";
        }
        if (persona.getCodigoPostal() > 0) {
            insrtStr = insrtStr + persona.getCodigoPostal() + ", ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getLocalidad())) {
            insrtStr = insrtStr + "'" + persona.getLocalidad() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getProvincia())) {
            insrtStr = insrtStr + "'" + persona.getProvincia() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getTelefono())) {
            insrtStr = insrtStr + "'" + persona.getTelefono() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getMovil())) {
            insrtStr = insrtStr + "'" + persona.getMovil() + "', ";
        }
        if (persona.getFecNac() != null) {
            insrtStr = insrtStr + " '" + util.DateUtil.convertToDatabaseColumn(persona.getFecNac()) + "', ";
        }
            insrtStr = insrtStr + idPerson + ") ";


        try {
            globalConnection = super.openDBConnection();
            statemt = globalConnection.createStatement();

            if (statemt.executeUpdate(insrtStr) == 1) {
                persona.setId(idPerson);
                return idPerson;
            } else {
                return -1;
            }
        } catch (DatabaseInUseException | NullPointerException | SQLException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } finally {
            try {
                if (statemt != null && !statemt.isClosed()) {
                    statemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return -1;
    }
    
    /**
     * Guarda los datos de una persona nueva
     * @param persona que va a a guardarse
     * @return código ID único identificador de la persona, -1 en caso de error
     */
    public int guardaPersona(PersonaDTO persona) {
        PreparedStatement ppStatemt = null;

        String insrtStr = "UPDATE PERSONA set ";
            insrtStr = insrtStr + "IDTIPOIDENTIFICADOR = " + persona.getIdentificador() + ", ";
            insrtStr = insrtStr + "IDENTIFICADOR = '" + persona.getDocumento() + "', ";
            insrtStr = insrtStr + " NOMBRE = '" + persona.getNombre() + "', ";
            insrtStr = insrtStr + "PAPELLIDO = '" + persona.getpApellido() + "', ";
        if (!ValidationsUtil.isCadenaVacia(persona.getsApellido())) {
            insrtStr = insrtStr + "   SAPELLIDO = '" + persona.getsApellido() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getDireccion())) {
            insrtStr = insrtStr + "   DIRECCION = '" + persona.getDireccion() + "', ";
        }
        if (persona.getCodigoPostal() > 0) {
            insrtStr = insrtStr + "   CODPOSTAL = " + persona.getCodigoPostal() + ", ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getLocalidad())) {
            insrtStr = insrtStr + "   LOCALIDAD = '" + persona.getLocalidad() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getProvincia())) {
            insrtStr = insrtStr + "   PROVINCIA = '" + persona.getLocalidad() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getTelefono())) {
            insrtStr = insrtStr + "   TELEFONO = '" + persona.getTelefono() + "', ";
        }
        if (!ValidationsUtil.isCadenaVacia(persona.getMovil())) {
            insrtStr = insrtStr + "   TELMOVIL = '" + persona.getMovil() + "', ";
        }
        if (persona.getFecNac() != null) {
            insrtStr = insrtStr + "   FECNAC = '" + util.DateUtil.convertToDatabaseColumn(persona.getFecNac()) + "', ";
        }
        insrtStr = insrtStr + "   FECMODIFICA = CURRENT_TIMESTAMP() "
                + " where ID = ? ";
        
        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(insrtStr);
            ppStatemt.setInt(1, persona.getId());

            if (ppStatemt.executeUpdate() == 1) {
                return persona.getId();
            } else {
                return -1;
            }
        } catch (DatabaseInUseException | NullPointerException | SQLException dbEx) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } finally {
            try {
                if (ppStatemt != null && !ppStatemt.isClosed()) {
                    ppStatemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return -1;
    }

    
    
    private int getSiguienteId() {

        ResultSet rs = null;
        Statement statemt = null;
        String checkStr = "select max(ID) + 1 AS ID "
                + " from PERSONA";

        try {
            globalConnection = super.openDBConnection();
            statemt = globalConnection.createStatement();

            rs = statemt.executeQuery(checkStr);

            if (rs.next()) {
                return rs.getInt("ID");
            }
            return 1;
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
        return -1;
    }
}
