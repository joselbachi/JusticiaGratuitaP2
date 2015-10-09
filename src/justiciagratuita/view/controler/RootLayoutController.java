/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.view.controler;

import javafx.fxml.FXML;
import justiciagratuita.JusticiaGratuita;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author joseluis.bachiller
 */
public class RootLayoutController {

    // Reference to the main application
    private JusticiaGratuita mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(JusticiaGratuita mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        //mainApp. getExpedientesData().clear();
        Dialogs.create()
            .title("Falta programación")
            .masthead("")
            .message("")
            .showInformation();
    }

    
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Dialogs.create()
            .title("Justicia Gratuita")
            .masthead("Acerca de")
            .message("Versión 1.0")
            .showInformation();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
