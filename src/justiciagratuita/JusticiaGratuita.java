/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justiciagratuita.dao.BaseDao;
import justiciagratuita.modelo.Usuario;
import justiciagratuita.security.Authenticator;
import justiciagratuita.view.controler.LoginController;
import justiciagratuita.view.controler.ProfileController;

/**
 *
 * @author joseluis.bachiller
 */
public class JusticiaGratuita extends Application {
    
    private Stage stage;
    private Usuario loggedUser;
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(JusticiaGratuita.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("Justicia Gratuita");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(JusticiaGratuita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() throws Exception {
        BaseDao cierra = new BaseDao();
        cierra.closeConexionUnica();
        super.stop(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(String userId, String password) {
        loggedUser = Authenticator.validate(userId, password);
        if (loggedUser != null && loggedUser.getIdUsuario() != null ) {
            gotoProfile();
            return true;
        } else {
            return false;
        }
    }

    public void userLogout() {
        loggedUser = null;
        gotoLogin();
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
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

}
