
package justiciagratuita.security;

import justiciagratuita.dao.UserDao;
import justiciagratuita.modelo.Usuario;


public class Authenticator {

    public static Usuario validate(String user, String password){
        UserDao userDB = new UserDao();
        Usuario usu;
        
        usu = userDB.checkUser(user, password);
        if (usu != null && usu.getIdUsuario() != null ) {
            return usu;
        } else {
            return null;
        }
    }
}