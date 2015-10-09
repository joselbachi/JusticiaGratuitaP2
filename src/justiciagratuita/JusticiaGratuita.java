/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import justiciagratuita.dao.BaseDao;
import justiciagratuita.dao.EstadoExpDao;
import justiciagratuita.dao.PersonaDao;
import justiciagratuita.exceptions.DatabaseInUseException;
import justiciagratuita.modelo.EstadoExpDTO;
import justiciagratuita.modelo.ExpedienteDTO;
import justiciagratuita.modelo.PersonaDTO;
import justiciagratuita.modelo.UsuarioDTO;
import justiciagratuita.modelo.logica.Expediente;
import justiciagratuita.security.Authenticator;
import justiciagratuita.view.controler.ExpedienteEditDialogController;
import justiciagratuita.view.controler.LoginController;
import justiciagratuita.view.controler.ExpedienteOwController;
import justiciagratuita.view.controler.PersonEditDialogController;
import justiciagratuita.view.controler.ProfileController;
import justiciagratuita.view.controler.RootLayoutController;
import org.controlsfx.dialog.Dialogs;
import util.DateUtil;

/**
 *
 * @author joseluis.bachiller
 */
public class JusticiaGratuita extends Application {

    private final boolean DEBUG = true;

    private Stage stage;
    private UsuarioDTO loggedUser;
    private final double MINIMUM_WINDOW_WIDTH = 850.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    private BorderPane rootLayout;

    private ObservableList<ExpedienteDTO> expedientesData;
    private EstadoExpDTO EstadoExpedientesData;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(JusticiaGratuita.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        expedientesData = cargaExpedientesInicio();
        try {
            stage = primaryStage;
            stage.setTitle("Justicia Gratuita");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            if (checkDatabase()) {
                if (!DEBUG) {
                    gotoLogin();
                } else {
                    intUsuario();
                    gotoRootLay();
                }
                primaryStage.show();
            }
        } catch (Exception ex) {
            Logger.getLogger(JusticiaGratuita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ObservableList<ExpedienteDTO> cargaExpedientesInicio() {
        Expediente expe = new Expediente();
        EstadoExpDao estaDao = new EstadoExpDao();
        EstadoExpedientesData = estaDao.getEstadoBy(Expediente.ESTADOTRAMITA);
        List<ExpedienteDTO> datos = expe.listExpedientesByEstado(EstadoExpedientesData);
        return FXCollections.observableArrayList(datos); 
    }

    @Override
    public void stop() throws Exception {
        BaseDao cierra = new BaseDao();
        cierra.closeConexionUnica();
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }

    public UsuarioDTO getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(String user, String password) {
        loggedUser = Authenticator.validate(user, password);
        if (loggedUser != null && loggedUser.getUsuario() != null) {
            PersonaDao personDB = new PersonaDao();
            PersonaDTO usu;
            usu = personDB.getPersonaByID(loggedUser.getIdPersona());
            loggedUser.setPersona(usu);
            if (user.equalsIgnoreCase(password)) {
                gotoProfile();
            } else {
                gotoRootLay();
            }
            return true;
        } else {
            return false;
        }
    }

    public void userLogout() {
        loggedUser = null;
        gotoLogin();
    }

    public void userVolver() {
        gotoRootLay();
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return stage;
    }

    private void gotoRootLay() {
        try {
            // Hay que cambiar por la pantalla de expedientes
            initRootLayout();
            showExpedienteOverview();

        } catch (Exception ex) {
            Logger.getLogger(JusticiaGratuita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoProfile() {
        try {
            ProfileController profile = (ProfileController) replaceSceneContent("view/profile.fxml");
            profile.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(JusticiaGratuita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("view/login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(JusticiaGratuita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = JusticiaGratuita.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(JusticiaGratuita.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, 800, 700);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JusticiaGratuita.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
        
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showExpedienteOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JusticiaGratuita.class.getResource("view/ExpedienteOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ExpedienteOwController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(PersonaDTO person) {
        try {
    // Load the fxml file and create a new stage for the popup
            //FXMLLoader loader = new FXMLLoader(JusticiaGratuita.class.getResource("view/PersonEditDialog.fxml"));
            //AnchorPane page = (AnchorPane) loader.load();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JusticiaGratuita.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean newExpedienteEditDialog(ExpedienteDTO expedt) {
        Expediente expe = new Expediente();
        expedt.setNumTurno(expe.siguienteTurno(Integer.parseInt(DateUtil.nowYear())));
        return showExpedienteEditDialog(expedt);
    }
    
    /**
     * Opens a dialog to edit details for the specified expediente. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param expedt the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showExpedienteEditDialog(ExpedienteDTO expedt) {
        try {
    // Load the fxml file and create a new stage for the popup
            //FXMLLoader loader = new FXMLLoader(JusticiaGratuita.class.getResource("view/PersonEditDialog.fxml"));
            //AnchorPane page = (AnchorPane) loader.load();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JusticiaGratuita.class.getResource("view/ExpedienteEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Expediente");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller
            ExpedienteEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setExpediente(expedt);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the data as an observable list of Persons.
     *
     * @return
     */
    public ObservableList<ExpedienteDTO> getExpedientesData() {
        return expedientesData;
    }
    
    public EstadoExpDTO getEstadoExpedientesData() {
        return EstadoExpedientesData;
    }
    
    /*
     Crea un usuario para conectarse en las pruebas sin tener que hacer loggin
     */

    private void intUsuario() {
        loggedUser = UsuarioDTO.of("bachi");
        loggedUser.setIdPersona(1);
        loggedUser.setPerfil("ADMIN");
        PersonaDao personDB = new PersonaDao();
        PersonaDTO usu;
        usu = personDB.getPersonaByID(loggedUser.getIdPersona());
        loggedUser.setPersona(usu);
    }

    private boolean checkDatabase() {
        BaseDao db = new BaseDao();
        try {
            if (!db.databaseIsOK()) {
              //  errorMessage.setText("Ha habido un problema al abrir la Base de Datos. Vuelva a intentarlo.");
                //com.sun.javafx.runtime.VersionInfo.getRuntimeVersion();
                //Dialogs.showErrorDialog(stage, 
                //        "Comprueba los mensajes de error para ver el problema", 
                //        "Se ha producido un error al abrir la Base de Datos", "Mensaje de error");
                Dialogs.create()
                        .title("Mensaje de error")
                        .masthead("Se ha producido un error al abrir la Base de Datos")
                        .message("Comprueba los mensajes de error para ver el problema")
                        .showError();
                return false;
            }
        } catch (DatabaseInUseException ex) {
            //Dialogs.showErrorDialog(stage, 
            //        "Comprueba si tienes otra aplicación que esté usando la Base de datos", 
            //        ex.getMessage(), "Mensaje de error");
            Dialogs.create()
                    .title("Mensaje de error")
                    .masthead("Se ha producido un error al abrir la Base de Datos")
                    .message("Comprueba si tienes otra aplicación que esté usando la Base de datos")
                    .showError();
            return false;
        }
        return true;
    }

}
