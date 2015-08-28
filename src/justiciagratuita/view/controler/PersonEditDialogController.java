/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.view.controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import justiciagratuita.modelo.PersonaDTO;
import util.CalendarUtil;

/**
 * FXML Controller class
 *
 * @author joseluis.bachiller
 */
public class PersonEditDialogController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField pApellidoField;
    @FXML
    private TextField sApellidoField;
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
    private TextField birthdayField;
 
    private Stage dialogStage;
    private PersonaDTO person;
    private boolean okClicked = false;
    
    ObservableList<String> options = FXCollections.observableArrayList(
        "NIF",
        "NIE",
        "PASAPORTE"
    );

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        idTipoIdentificadorField.setItems(options);
    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setPerson(PersonaDTO person) {
        this.person = person;

      nombreField.setText(person.getNombre());
      pApellidoField.setText(person.getpApellido());
      sApellidoField.setText(person.getsApellido());
      idTipoIdentificadorField.setValue(person.getIdTipoIdentificador());
      identificadorField.setText(person.getIdentificador());
      direccionField.setText(person.getDireccion());
      codigoPostalField.setText(Integer.toString(person.getCodigoPostal()));
      localidadField.setText(person.getLocalidad());
      provinciaField.setText(person.getProvincia());
      telefonoField.setText(person.getTelefono());
      movilField.setText(person.getMovil());
      birthdayField.setText(CalendarUtil.format(person.getBirthday()));
      birthdayField.setPromptText("dd-mm-yyyy");
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
            person.setNombre(nombreField.getText());
            person.setpApellido(pApellidoField.getText());
            person.setDireccion(direccionField.getText());
            person.setCodigoPostal(Integer.parseInt(codigoPostalField.getText()));
            person.setLocalidad(localidadField.getText());
            person.setBirthday(CalendarUtil.parse(birthdayField.getText()));

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

        if (nombreField.getText() == null || nombreField.getText().length() == 0) {
            errorMessage += "El nombre está vacío!\n"; 
        }
        if (pApellidoField.getText() == null || pApellidoField.getText().length() == 0) {
            errorMessage += "El primer apellido está vacío!\n"; 
        }
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

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Fecha de nacimiento no válida!\n";
        } else {
            if (!CalendarUtil.validString(birthdayField.getText())) {
                errorMessage += "Fecha de nacimiento no válida. Formato dd/mm/yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message
            Dialogs.showErrorDialog(dialogStage, errorMessage,
                    "Por favor corrija los errores", "Error en los campos");
            return false;
        }
    }
}
