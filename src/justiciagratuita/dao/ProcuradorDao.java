/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao;

import justiciagratuita.modelo.ProcuradorDTO;
import justiciagratuita.modelo.PersonaDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class ProcuradorDao extends PersonaDao {
    
    public ProcuradorDTO getProcuradorByID(int id) {
        PersonaDTO per = super.getPersonaByID(id);
        ProcuradorDTO let = new ProcuradorDTO();
        if (per != null) {
            return let.parse(per);
        } else {
            return null;
        }
    }
}
