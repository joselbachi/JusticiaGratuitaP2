/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo.logica;

import java.util.List;
import justiciagratuita.dao.ExpedienteDao;
import justiciagratuita.modelo.ExpedienteDTO;

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
     * @return expediente con los datos completos.
     */
    public ExpedienteDTO recuperaDatosExpedi(ExpedienteDTO exped){

        
        return exped;
    }
    
}
