/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package justiciagratuita.modelo;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private static final Map<String, Usuario> USERS = new HashMap<String, Usuario>();

    public static Usuario of(String id) {
        Usuario user = USERS.get(id);
        if (user == null) {
            user = new Usuario(id);
            USERS.put(id, user);
        }
        return user;
    }

    private Usuario(String id) {
        this.id = id;
    }
    private String id;

    public String getId() {
        return id;
    }
    
    private String nombre = "";
    private String passwd = "";
    private int perfil = 0;

    /**
     * @return el nombre de usuario
     */
    public String getNombre() {
        return nombre;
    }

     /**
     * @param nombre el nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return la contraseña del usuario
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @param passwd la contraseña del usuario
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Código del perfíl del usuario. Ver Usuario
     * @return 
     */
    public int getPerfil() {
        return perfil;
    }

    /**
     * Código del perfíl del usuario. Ver Usuario
     */
    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }
    
}
