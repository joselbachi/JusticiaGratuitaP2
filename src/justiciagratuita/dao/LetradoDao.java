/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao;

import justiciagratuita.modelo.LetradoDTO;
import justiciagratuita.modelo.PersonaDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class LetradoDao extends PersonaDao {
    
    
    public LetradoDTO getLetradoByID(int id) {
        PersonaDTO per = super.getPersonaByID(id);
        LetradoDTO let = new LetradoDTO();
        if (per != null) {
            return let.parse(per);
        } else {
            return null;
        }
    }
    
}
