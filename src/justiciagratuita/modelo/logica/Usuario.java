/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo.logica;

import justiciagratuita.dao.UsuarioDao;
import justiciagratuita.modelo.UsuarioDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class Usuario extends BaseLogic {
    
    public boolean updateUser(UsuarioDTO user) {
     
        if (user != null && user.getIdPersona() > 0 ) {
            UsuarioDao userDB = new UsuarioDao();
            
            userDB.updateUser(user);
            return true;
        } else {
            return false;
        }
    }
}
