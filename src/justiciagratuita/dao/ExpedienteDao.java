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
import justiciagratuita.modelo.ExpedienteDTO;
import justiciagratuita.modelo.PersonaDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class ExpedienteDao extends BaseDao {
    
    public ExpedienteDTO getExpedienteByID(int id) {

        ExpedienteDTO objeto;
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "ID, IDSOLICITANTE, IDJUZGADO, IDLETRADO, IDPROCURADOR, NUMTURNO, "
                + "ANYO, IDCOMISION, VIOLENCIA, FECENTRADACOL, FECENVIODEL, FECENTRADADEL, FECRESOLUCION, "
                + "FECIMPUGNACION, OBSERVACIONES, IDESTADO, FECALTA, FECMODIFICA "
                + " from EXPEDIENTE where "
                + "ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, id);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                PersonaDao per = new PersonaDao();
                objeto = new ExpedienteDTO(rs.getInt("id"), rs.getInt("idsolicitante"), rs.getInt("numTurno"), 
                        rs.getInt("anyo"), util.DateUtil.convertToEntityAttribute(rs.getTimestamp("fecEntradaCol")), 
                        rs.getInt("idEstado"), per.getPersonaByID(rs.getInt("id")));
                objeto.setIdJuzgado(rs.getInt("idJuzgado"));
                objeto.setIdLetrado(rs.getInt("idLetrado"));
                objeto.setIdProcurador(rs.getInt("idProcurador"));
                objeto.setIdComision(rs.getInt("idComision"));
                objeto.setViolencia(rs.getBoolean("violencia"));
                objeto.setFecEnvioDel(util.DateUtil.convertToEntityAttribute(rs.getDate("fecEnvioDel")));
                objeto.setFecEntradaDel(util.DateUtil.convertToEntityAttribute(rs.getDate("fecEntradaDel")));
                objeto.setFecResolucion(util.DateUtil.convertToEntityAttribute(rs.getDate("fecResolucion")));
                objeto.setFecImpugancion(util.DateUtil.convertToEntityAttribute(rs.getDate("fecImpugnacion")));
                objeto.setObservaciones(rs.getString("observaciones"));
                return objeto;
            }
        } catch (DatabaseInUseException dbEx) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } catch (NullPointerException nex) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, nex);
        } catch (SQLException ex) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ppStatemt != null && !ppStatemt.isClosed() ) {
                    ppStatemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
    public List<ExpedienteDTO> listExpedienteByEstado (int idEstado) {
        List<ExpedienteDTO> expedienteData = new ArrayList();
        ExpedienteDTO objeto; 
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "SELECT ID, IDSOLICITANTE, IDJUZGADO, IDLETRADO, IDPROCURADOR, NUMTURNO, "
                + "ANYO, IDCOMISION, VIOLENCIA, FECENTRADACOL, FECENVIODEL, FECENTRADADEL, FECRESOLUCION, "
                + "FECIMPUGNACION, OBSERVACIONES, IDESTADO, FECALTA, FECMODIFICA "
                + "from EXPEDIENTE where "
                + "IDESTADO = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, idEstado);

            rs = ppStatemt.executeQuery();

            while (rs.next()) {
                PersonaDao per = new PersonaDao();
                
                objeto = new ExpedienteDTO(rs.getInt("id"), rs.getInt("idsolicitante"), rs.getInt("numTurno"), 
                        rs.getInt("anyo"), util.DateUtil.convertToEntityAttribute(rs.getTimestamp("fecEntradaCol")), 
                        rs.getInt("idEstado"), per.getPersonaByID(rs.getInt("id")));
// Por ahora sólo necesito la lista
/*                objeto.setIdJuzgado(rs.getInt("idJuzgado"));
                objeto.setIdLetrado(rs.getInt("idLetrado"));
                objeto.setIdProcurador(rs.getInt("idProcurador"));
                objeto.setIdComision(rs.getInt("idComision"));
                objeto.setViolencia(rs.getBoolean("violencia"));
                objeto.setFecEnvioDel(util.DateUtil.convertToEntityAttribute(rs.getDate("fecEnvioDel")));
                objeto.setFecEntradaDel(util.DateUtil.convertToEntityAttribute(rs.getDate("fecEntradaDel")));
                objeto.setFecResolucion(util.DateUtil.convertToEntityAttribute(rs.getDate("fecResolucion")));
                objeto.setFecImpugancion(util.DateUtil.convertToEntityAttribute(rs.getDate("fecImpugnacion")));
                objeto.setObservaciones(rs.getString("observaciones"));
        */
                expedienteData.add(objeto);
            }
            return expedienteData;
        } catch (DatabaseInUseException dbEx) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } catch (NullPointerException nex) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, nex);
        } catch (SQLException ex) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ppStatemt != null && !ppStatemt.isClosed() ) {
                    ppStatemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
    /**
     * Lista los expedientes que están en un estado con sus datos completos. Incluye:
     *  solicitante: datos reducidos del Solicitante.
     * @param idEstado
     * @return 
     */
    public List<ExpedienteDTO> listExpedCompletoByEstado (int idEstado) {
        List<ExpedienteDTO> expedienteData = new ArrayList();
        ExpedienteDTO objeto; 
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "ID, IDSOLICITANTE, IDJUZGADO, IDLETRADO, IDPROCURADOR, NUMTURNO, "
                + "ANYO, IDCOMISION, VIOLENCIA, FECENTRADACOL, FECENVIODEL, FECENTRADADEL, FECRESOLUCION, "
                + "FECIMPUGNACION, OBSERVACIONES, IDESTADO, FECALTA, FECMODIFICA "
                + " from EXPEDIENTE where "
                + "IDESTADO = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, idEstado);

            rs = ppStatemt.executeQuery();
            
            PersonaDao personaDb = new PersonaDao();

            while (rs.next()) {
                PersonaDao per = new PersonaDao();
                objeto = new ExpedienteDTO(rs.getInt("id"), rs.getInt("idsolicitante"), rs.getInt("numTurno"), 
                        rs.getInt("anyo"), util.DateUtil.convertToEntityAttribute(rs.getTimestamp("fecEntradaCol")), 
                        rs.getInt("idEstado"), per.getPersonaByID(rs.getInt("id")));
                PersonaDTO solic = new PersonaDTO();
// Por ahora sólo necesito la lista
/*                objeto.setIdJuzgado(rs.getInt("idJuzgado"));
                objeto.setIdLetrado(rs.getInt("idLetrado"));
                objeto.setIdProcurador(rs.getInt("idProcurador"));
                objeto.setIdComision(rs.getInt("idComision"));
                objeto.setViolencia(rs.getBoolean("violencia"));
                objeto.setFecEnvioDel(util.DateUtil.convertToEntityAttribute(rs.getDate("fecEnvioDel")));
                objeto.setFecEntradaDel(util.DateUtil.convertToEntityAttribute(rs.getDate("fecEntradaDel")));
                objeto.setFecResolucion(util.DateUtil.convertToEntityAttribute(rs.getDate("fecResolucion")));
                objeto.setFecImpugancion(util.DateUtil.convertToEntityAttribute(rs.getDate("fecImpugnacion")));
                objeto.setObservaciones(rs.getString("observaciones"));
        */
                expedienteData.add(objeto);
            }
            return expedienteData;
        } catch (DatabaseInUseException dbEx) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, dbEx);
        } catch (NullPointerException nex) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, nex);
        } catch (SQLException ex) {
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (ppStatemt != null && !ppStatemt.isClosed() ) {
                    ppStatemt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            super.closeDBConnection(globalConnection);
        }
        return null;
    }
    
    private ExpedienteDTO recuperaDatos(ExpedienteDTO exped) {
        
        
        return exped;
    }
}
