
package justiciagratuita.security;

import justiciagratuita.dao.UsuarioDao;
import justiciagratuita.modelo.UsuarioDTO;


public class Authenticator {

    public static UsuarioDTO validate(String user, String password){
        UsuarioDao userDB = new UsuarioDao();
        UsuarioDTO usu;
        
        usu = userDB.checkUser(user, password);
        if (usu != null && usu.getUsuario() != null ) {
            return usu;
        } else {
            return null;
        }
    }
}