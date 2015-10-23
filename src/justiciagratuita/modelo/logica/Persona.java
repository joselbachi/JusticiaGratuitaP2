/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo.logica;

import justiciagratuita.dao.PersonaDao;
import justiciagratuita.modelo.PersonaDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class Persona extends BaseLogic {
    
    /**
     * Guarda los datos de la persona en la BBDD
     * @param persona datos de la persona a guardar
     * @return código único (id) de la nueva persona
     */
    public int guardaNewPersona (PersonaDTO persona) {
        PersonaDao persondao = new PersonaDao();
        int idPers =  persondao.guardaNewPersona(persona);
        if (idPers > 0 ) {
            persona.setId(idPers);
        }
        return idPers;
    }
    
    /**
     * Guarda los datos de la persona en la BBDD
     * @param persona datos de la persona a guardar
     * @return código único (id) de la nueva persona
     */
    public int guardaPersona (PersonaDTO persona) {
        PersonaDao persondao = new PersonaDao();
        int idPers =  persondao.guardaNewPersona(persona);
        if (idPers > 0 ) {
            persona.setId(idPers);
        }
        return idPers;
    }
}
