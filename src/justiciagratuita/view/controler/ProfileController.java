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
package justiciagratuita.view.controler;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import justiciagratuita.JusticiaGratuita;
import justiciagratuita.modelo.PersonaDTO;
import justiciagratuita.modelo.UsuarioDTO;
import justiciagratuita.modelo.logica.Usuario;

/**
 * Profile Controller.
 */
public class ProfileController extends AnchorPane {

    @FXML
    private TextField idPersona;
    @FXML
    private TextField usuario;
    @FXML
    private TextField contrasena;
    @FXML
    private TextField contrasenaRep;
    @FXML
    private TextField nombre;
    @FXML
    private TextField perfil;
    @FXML
    private Hyperlink logout;
    @FXML
    private Hyperlink volver;
    @FXML 
    private Button save;  
    @FXML 
    private Label success;
    
    private JusticiaGratuita application;
    
    public void setApp(JusticiaGratuita application){
        this.application = application;
        UsuarioDTO loggedUser = application.getLoggedUser();
        PersonaDTO person = loggedUser.getPersona();
        idPersona.setText(String.valueOf(loggedUser.getIdPersona()));
        usuario.setText(loggedUser.getUsuario());
        contrasena.setText(loggedUser.getPasswd());
        nombre.setText(loggedUser.getNombre()); // person.getNombre();
        
//        if (loggedUser.getPerfil() ) {
  //          perfil.setText(String.valueOf(loggedUser.getPerfil()));
  //      }
        success.setOpacity(0);
    }

    public void processLogout(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }
        
        application.userLogout();
    }
    
    public void processVolver(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }
        
        application.userVolver();
    }
    
    public void saveProfile(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            animateMessage();
            return;
        }
        if (contrasena.getText().equals(contrasenaRep.getText())) {
            UsuarioDTO loggedUser = application.getLoggedUser();
            //loggedUser.setNombre(usuario.getText());
            loggedUser.setPasswd(contrasena.getText());
//            loggedUser.setPerfil(Integer.parseInt(perfil.getText()));
            success.setText("Salvando datos");
            animateMessage();
            Usuario usuaL = new Usuario();
            if (usuaL.updateUser(loggedUser)) {
                success.setText("Datos guardados");
            } else {
                success.setText("No se han podido salvar los datos.");
            }
        } else {
            success.setText("Las dos contraseñas no coinciden. Deben ser iguales");
        }
        animateMessage();
    }
    
    public void resetProfile(ActionEvent event){
        if (application == null){
            return;
        }
        contrasenaRep.setText("");
        success.setOpacity(0.0);
        
    }

    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
}
