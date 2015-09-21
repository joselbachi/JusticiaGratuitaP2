/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.view.controler;

import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import justiciagratuita.dao.PersonaDao;
import justiciagratuita.dao.TauxiliaresDao;
import justiciagratuita.modelo.ExpedienteDTO;
import justiciagratuita.modelo.PersonaDTO;
import org.controlsfx.dialog.Dialogs;
import util.DateUtil;
import util.ValidationsUtil;

/**
 * FXML Controller class
 *
 * @author joseluis.bachiller
 */
public class ExpedienteEditDialogController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidosField;
    @FXML
    private ComboBox idTipoIdentificadorField;
    @FXML
    private TextField identificadorField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField codigoPostalField;
    @FXML
    private TextField localidadField;
    @FXML
    private TextField provinciaField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField movilField;
    @FXML
    private TextField fecNacField;
 
    private Stage dialogStage;
    private ExpedienteDTO expdte;
    private boolean okClicked = false;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        idTipoIdentificadorField.setItems(itemsTIdentificador());
        idTipoIdentificadorField.setPromptText("Tipo doc.");
        nombreField.setEditable(false);
        apellidosField.setEditable(false);
        
        // control
        identificadorField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                postDocumento();
                if (!ValidationsUtil.isCadenaVacia(ValidationsUtil.validaDocumento(identificadorField.getText(), (String) idTipoIdentificadorField.getValue()))) {
                    Dialogs.create()
                        .title("Error en el identificador")
                        .masthead("Hay errores en algunos campos")
                        .message(ValidationsUtil.validaDocumento(identificadorField.getText(), (String) idTipoIdentificadorField.getValue())+
                                "\nPor favor introduzca un "+ (String )idTipoIdentificadorField.getValue() + " válido.")
                        .showError();
                    identificadorField.requestFocus();
                            // Hacer que el foco se quede en el campo, si hay error
                } else {
                    recuperaPersona((String) idTipoIdentificadorField.getValue(), identificadorField.getText());
                }
            }
        });
    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the expediente to be edited in the dialog.
     * 
     * @param expdte
     */
    public void setExpediente(ExpedienteDTO expdte) {
        this.expdte = expdte;

      nombreField.setText(expdte.getSolicitanteNombre());
      apellidosField.setText(expdte.getSolicitanteNombre());
      //idTipoIdentificadorField.setItems(options);
    /*  idTipoIdentificadorField.setValue(expdte.getIdTipoIdentificador());
      identificadorField.setText(expdte.getIdentificador());
      direccionField.setText(expdte.getDireccion());
      codigoPostalField.setText(Integer.toString(expdte.getCodigoPostal()));
      localidadField.setText(expdte.getLocalidad());
      provinciaField.setText(expdte.getProvincia());
      telefonoField.setText(expdte.getTelefono());
      movilField.setText(expdte.getMovil());
      fecNacField.setText(DateUtil.format(expdte.getFecNac()));
      fecNacField.setPromptText("dd-mm-yyyy");
      */
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
         /*   expdte.setNombre(nombreField.getText());
            expdte.setpApellido(pApellidoField.getText());
            expdte.setDireccion(direccionField.getText());
            expdte.setCodigoPostal(Integer.parseInt(codigoPostalField.getText()));
            expdte.setLocalidad(localidadField.getText());
            expdte.setFecNac(DateUtil.parse(fecNacField.getText()));

            expdte.setsApellido(sApellidoField.getText());
            expdte.setIdTipoIdentificador((String) idTipoIdentificadorField.getValue());
            expdte.setIdentificador(identificadorField.getText());
            expdte.setProvincia(provinciaField.getText());
            expdte.setTelefono(telefonoField.getText());
            expdte.setMovil(movilField.getText());
*/
            Dialogs.create()
                .title("Falta programar")
                .masthead("Aún no está programada la acción")
                .message("Aún no está programada la acción")
                .showError();

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (ValidationsUtil.isCadenaVacia(identificadorField.getText())) {
            errorMessage += "El documento identificador está vacío!\n"; 
        } else {
            if (!ValidationsUtil.isCadenaVacia(ValidationsUtil.validaDocumento(identificadorField.getText(), (String) idTipoIdentificadorField.getValue()))) {
                errorMessage += ValidationsUtil.validaDocumento(identificadorField.getText(), (String) idTipoIdentificadorField.getValue())+"!\n"; 
            }
        }
        

        if (nombreField.getText() == null || nombreField.getText().length() == 0) {
            errorMessage += "El nombre está vacío!\n"; 
        }
        /*
        if (pApellidoField.getText() == null || pApellidoField.getText().length() == 0) {
            errorMessage += "El primer apellido está vacío!\n"; 
        }
        */
        if (direccionField.getText() == null || direccionField.getText().length() == 0) {
            errorMessage += "La dirección está vacía!\n"; 
        }

        if (codigoPostalField.getText() == null || codigoPostalField.getText().length() != 5) {
            errorMessage += "El código postal esta vacío o no es válido!\n"; 
        } else {
            // try to parse the postal code into an int
            try {
                Integer.parseInt(codigoPostalField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "El código postal no es válido (debe ser numérico)!\n"; 
            }
        }

        if (localidadField.getText() == null || localidadField.getText().length() == 0) {
            errorMessage += "Localidad no válida!\n"; 
        }
        
        if (provinciaField.getText() == null || provinciaField.getText().length() == 0) {
            errorMessage += "Provincia no válida!\n"; 
        }

        if (fecNacField.getText() == null || fecNacField.getText().length() == 0) {
            errorMessage += "Fecha de nacimiento no válida!\n";
        } else {
            if (!DateUtil.validDate(fecNacField.getText())) {
                errorMessage += "Fecha de nacimiento no válida. Formato dd/mm/yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message
            //Dialogs.showErrorDialog(dialogStage, errorMessage,
            //        "Por favor corrija los errores", "Error en los campos");
            Dialogs.create()
                .title("Error en los campos")
                .masthead("Hay errores en algunos campos")
                .message(errorMessage+"\nPor favor corrija los errores")
                .showError();
            return false;
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void postDocumento() {
        ValidationsUtil valida = new ValidationsUtil();
        identificadorField.setText(valida.preparaDocumento(identificadorField.getText()));
    }
    
    private ObservableList<String> itemsTIdentificador() {
        TauxiliaresDao db = new TauxiliaresDao();
        List<String>  list =   db.getListTipoDocumentoStr();
        ObservableList<String> options = FXCollections.observableArrayList(list);
        
        return options;
    }
    
    private void recuperaPersona(String tipoDoc, String documento) {
        PersonaDao per = new PersonaDao();
        PersonaDTO solic = per.getPersonaByDocu(tipoDoc, documento);
        if (solic == null) {
           Dialogs.create()
                .title("Falta programación")
                .masthead("Falta código por programar.")
                .message("Tenemos que llamar a la pantalla para crear la persona")
                .showError(); 
        } else {
            nombreField.setText(solic.getNombre());
            apellidosField.setText(solic.getApellidos());
        }
    }
}

