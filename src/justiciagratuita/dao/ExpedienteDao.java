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
import justiciagratuita.modelo.EstadoExpDTO;
import justiciagratuita.modelo.ExpedienteDTO;
import util.ValidationsUtil;

/**
 *
 * @author joseluis.bachiller
 */
public class ExpedienteDao extends BaseDao {
    
    public ExpedienteDTO getExpedienteByID(int id) {

        ExpedienteDTO objeto;
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, IDSOLICITANTE, IDJUZGADO, IDLETRADO, IDPROCURADOR, NUMTURNO, "
                + "ANYO, IDCOMISION, VIOLENCIA, FECENTRADACOL, FECENVIODEL, FECENTRADADEL, FECRESOLUCION, "
                + "FECIMPUGNACION, OBSERVACIONES, IDESTADO, FECALTA, FECMODIFICA, IDASUNTO "
                + "from EXPEDIENTE "
                + "where ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, id);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                PersonaDao per = new PersonaDao();
                TasuntoDao asun = new TasuntoDao();
                JuzgadoDao juz = new JuzgadoDao();
                LetradoDao let = new LetradoDao();
                ProcuradorDao pro = new ProcuradorDao();
                EstadoExpDao est = new EstadoExpDao();
                objeto = new ExpedienteDTO(rs.getInt("id"), rs.getInt("numTurno"), 
                        rs.getInt("anyo"), util.DateUtil.convertToEntityAttribute(rs.getTimestamp("fecEntradaCol")), 
                        est.getEstadoBy(rs.getInt("idEstado")), per.getPersonaByID(rs.getInt("IDSOLICITANTE")), asun.getTasuntoBy(rs.getInt("idAsunto")));
                objeto.setJuzgado(juz.getJuzgadoByStr(rs.getInt("idJuzgado")));
                objeto.setLetrado(let.getLetradoByID(rs.getInt("idLetrado")));
                objeto.setProcurador(pro.getProcuradorByID(rs.getInt("idProcurador")));
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
    
    public List<ExpedienteDTO> listExpedienteByEstado (EstadoExpDTO estado) {
        List<ExpedienteDTO> expedienteData = new ArrayList();
        ExpedienteDTO objeto; 
        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select ID, IDSOLICITANTE, IDJUZGADO, IDLETRADO, IDPROCURADOR, NUMTURNO, "
                + "ANYO, IDCOMISION, VIOLENCIA, FECENTRADACOL, FECENVIODEL, FECENTRADADEL, FECRESOLUCION, "
                + "FECIMPUGNACION, OBSERVACIONES, IDESTADO, FECALTA, FECMODIFICA, IDASUNTO "
                + "from EXPEDIENTE "
                + "where IDESTADO = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, estado.getId());

            rs = ppStatemt.executeQuery();

            while (rs.next()) {
                PersonaDao per = new PersonaDao();
                TasuntoDao asun = new TasuntoDao();
                EstadoExpDao est = new EstadoExpDao();
                objeto = new ExpedienteDTO(rs.getInt("id"), rs.getInt("numTurno"), 
                        rs.getInt("anyo"), util.DateUtil.convertToEntityAttribute(rs.getTimestamp("fecEntradaCol")), 
                        est.getEstadoBy(rs.getInt("idEstado")), per.getPersonaByID(rs.getInt("IDSOLICITANTE")), asun.getTasuntoBy(rs.getInt("idAsunto")));
                // Por ahora sólo necesito la lista
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
    
    public int getSiguienteTurno(int anyo) {

        ResultSet rs = null;
        PreparedStatement ppStatemt = null;
        String checkStr = "select max(NUMTURNO) + 1 AS NUMTURNO "
                + " from EXPEDIENTE"
                + " where ANYO = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(checkStr);
            ppStatemt.setInt(1, anyo);

            rs = ppStatemt.executeQuery();

            if (rs.next()) {
                return rs.getInt("NUMTURNO");
            }
            return 1;
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
        return -1;
    }
    
    private int getSiguienteId() {

        ResultSet rs = null;
        Statement statemt = null;
        String checkStr = "select max(ID) + 1 AS ID "
                + " from EXPEDIENTE";

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

    /**
     * Guarda los datos de un expediente nuevo
     * @param expediente que va a a guardarse
     * @return código ID único identificador del expediente, -1 en caso de error
     */
    public int guardaNewExpediente(ExpedienteDTO expediente) {
        Statement statemt = null;
        int idExped = getSiguienteId();
        String insrtStr = "Insert into Expediente (ID, IDSOLICITANTE, ";
        if (expediente.getJuzgado() != null && expediente.getJuzgado().getId() > 0) {
            insrtStr = insrtStr + "   IDJUZGADO, ";
        }
        if (expediente.getLetrado() != null && expediente.getLetrado().getId() > 0) {
            insrtStr = insrtStr + "   IDLETRADO, ";
        }
        if (expediente.getProcurador() != null && expediente.getProcurador().getId() > 0) {
            insrtStr = insrtStr + "   IDPROCURADOR, ";
        }
        insrtStr = insrtStr
                + "   NUMTURNO, "
                + "   ANYO, "
                + "   IDASUNTO, "
                + "   FECENTRADACOL, ";
        if (expediente.getFecEntradaDel() != null) {
            insrtStr = insrtStr + "   FECENTRADADEL, ";
        }
        insrtStr = insrtStr
                + "   IDESTADO) "
                + " values ("
                + idExped + ", "
                + expediente.getSolicitante().getId() + ", ";
        if (expediente.getJuzgado() != null && expediente.getJuzgado().getId() > 0) {
            insrtStr = insrtStr + expediente.getJuzgado().getId() + ", ";
        }
        if (expediente.getLetrado() != null && expediente.getLetrado().getId() > 0) {
            insrtStr = insrtStr + expediente.getLetrado().getId() + ", ";
        }
        if (expediente.getProcurador() != null && expediente.getProcurador().getId() > 0) {
            insrtStr = insrtStr + expediente.getProcurador().getId() + ", ";
        }
        insrtStr = insrtStr
                + expediente.getNumTurno() + ", "
                + expediente.getAnyo() + ", "
                + expediente.asuntoProperty().getValue().getId() + ", "
                + " '" + util.DateUtil.convertToDatabaseColumn(expediente.getFecEntradaCol()) + "', ";
        if (expediente.getFecEntradaDel() != null) {
            insrtStr = insrtStr + " '" + util.DateUtil.convertToDatabaseColumn(expediente.getFecEntradaDel()) + "', ";
        }
        insrtStr = insrtStr
                + expediente.getEstado().getId() + ") ";

        try {
            globalConnection = super.openDBConnection();
            statemt = globalConnection.createStatement();

            if (statemt.executeUpdate(insrtStr) == 1) {
                expediente.setId(idExped);
                return idExped;
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
     * Guarda los datos de un expediente existente
     * @param expediente que va a a guardarse
     * @return código ID único identificador del expediente, -1 en caso de error
     */
    public int guardaExpediente(ExpedienteDTO expediente) {
        PreparedStatement ppStatemt = null;

        String insrtStr = "UPDATE Expediente set ";
        if (expediente.getJuzgado() != null && expediente.getJuzgado().getId() > 0) {
            insrtStr = insrtStr + "   IDJUZGADO = ";
            insrtStr = insrtStr + expediente.getJuzgado().getId() + ", ";
        } else {
            insrtStr = insrtStr + "   IDJUZGADO = null, ";
        }
        if (expediente.getLetrado() != null && expediente.getLetrado().getId() > 0) {
            insrtStr = insrtStr + "   IDLETRADO = ";
            insrtStr = insrtStr + expediente.getLetrado().getId() + ", ";
        } else {
            insrtStr = insrtStr + "   IDLETRADO = null, ";
        }
        if (expediente.getProcurador() != null && expediente.getProcurador().getId() > 0) {
            insrtStr = insrtStr + "   IDPROCURADOR = ";
            insrtStr = insrtStr + expediente.getProcurador().getId() + ", ";
        } else {
            insrtStr = insrtStr + "   IDPROCURADOR = null, ";
        }
        if (expediente.getNumExped() > 0 ) {
            insrtStr = insrtStr + "   NUMEXPED = ";
            insrtStr = insrtStr + expediente.getNumExped() + ", ";
        } else {
            insrtStr = insrtStr + "   NUMEXPED = null, ";
        }
        insrtStr = insrtStr + "   IDASUNTO = ";
        insrtStr = insrtStr + expediente.asuntoProperty().getValue().getId() + ", ";
        if (expediente.getViolencia() != null ) {
            insrtStr = insrtStr + "   VIOLENCIA = ";
            insrtStr = insrtStr + expediente.getViolencia() + ", ";
        } else {
            insrtStr = insrtStr + "   VIOLENCIA = null, ";
        }
        if (!ValidationsUtil.isCadenaVacia(expediente.getObservaciones())) {
            insrtStr = insrtStr + "   OBSERVACIONES = '";
            insrtStr = insrtStr + expediente.getObservaciones() + "', ";
        } else {
            insrtStr = insrtStr + "   OBSERVACIONES = null, ";
        }
        insrtStr = insrtStr + "   FECMODIFICA = CURRENT_TIMESTAMP() "
                + " where ID = ? ";

        try {
            globalConnection = super.openDBConnection();
            ppStatemt = globalConnection.prepareStatement(insrtStr);
            ppStatemt.setInt(1, expediente.getId());

            if (ppStatemt.executeUpdate() == 1) {
                return expediente.getId();
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
}
