/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo.logica;

import java.util.List;
import justiciagratuita.dao.ExpedienteDao;
import justiciagratuita.modelo.EstadoExpDTO;
import justiciagratuita.modelo.ExpedienteDTO;
import util.DateUtil;

/**
 *
 * @author joseluis.bachiller
 */
public class Expediente extends BaseLogic {
    
    private final static int ESTADOTRAMITA = 1;
    
    
    public List<ExpedienteDTO> listExpedienteEnTramite() {
        ExpedienteDao dao = new ExpedienteDao();
        return dao.listExpedienteByEstado(ESTADOTRAMITA);
    }
    
    /**
     * Recupera los datos auxiliares de un expediente. El expediente tiene los datos básicos 
     * (tabla expediente + solicitante) y se recuperan el resto de los datos.
     * @param exped
     * @return expediente con los datos completos.
     */
    public ExpedienteDTO recuperaDatosExpedi(ExpedienteDTO exped){
        ExpedienteDao expedao = new ExpedienteDao();
        exped = expedao.getExpedienteByID(exped.getId());
        return exped;
    }
    
    public int siguienteTurno (int anyo) {
        ExpedienteDao expedao = new ExpedienteDao();
        return expedao.getSiguienteTurno(anyo);
    }
    
    /**
     * por ahora no se usa
     * @return 
     */
    @Deprecated
    public ExpedienteDTO newExpedi(){
        ExpedienteDao expedao = new ExpedienteDao();
        ExpedienteDTO expediente = new ExpedienteDTO();
        expediente.setNumTurno(expedao.getSiguienteTurno(Integer.parseInt(DateUtil.nowYear())));
        return expediente;
    }
    
    /**
     * Guarda los datos del expediente en la BBDD
     * @param expediente datos del expediente a guardar
     * @return código único (id) del nuevo expediente
     */
    public int guardaNewExpediente (ExpedienteDTO expediente) {
        ExpedienteDao expedao = new ExpedienteDao();
        EstadoExpDTO estado = new EstadoExpDTO(ESTADOTRAMITA, "", "");
        expediente.setEstado(estado);
        int idExpe =  expedao.guardaNewExpediente(expediente);
        if (idExpe > 0 ) {
            expediente.setId(idExpe);
        }
        return idExpe;
    }
}
