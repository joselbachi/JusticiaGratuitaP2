/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.view.controler;

import java.io.IOException;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import justiciagratuita.JusticiaGratuita;
import justiciagratuita.dao.PersonaDao;
import justiciagratuita.dao.TasuntoDao;
import justiciagratuita.dao.TdocumentoDao;
import justiciagratuita.dao.JuzgadoDao;
import justiciagratuita.modelo.ExpedienteDTO;
import justiciagratuita.modelo.JuzgadoDTO;
import justiciagratuita.modelo.PersonaDTO;
import justiciagratuita.modelo.TasuntoDTO;
import justiciagratuita.modelo.TdocumentoDTO;
import justiciagratuita.modelo.logica.Expediente;
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
    private TextField anyoField;
    @FXML
    private TextField turnoField;
    @FXML
    private TextField expedField;
    @FXML
    private ComboBox tipoIdentificadorField;
    @FXML
    private Label solicitanteNombreLabel;
    @FXML
    private ComboBox asuntoField;
    @FXML
    private TextField identificadorField;
    @FXML
    private ComboBox juzgadoField;
    @FXML
    private Label direccionLabel;
    @FXML
    private Label codigoPostalLabel;
    @FXML
    private Label localidadLabel;
    @FXML
    private Label provinciaLabel;
    @FXML
    private Label telefonoLabel;
    @FXML
    private Label movilLabel;
    @FXML
    private TextField fecEntradaColField;

    private Stage dialogStage;
    private ExpedienteDTO expdte;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        anyoField.setText(DateUtil.nowYear());
        turnoField.setEditable(false);
        turnoField.setDisable(true);
        expedField.setEditable(false);
        expedField.setDisable(true);
        tipoIdentificadorField.setItems(itemsTipoDocumento());
        tipoIdentificadorField.setPromptText("Tipo doc.");
        asuntoField.setItems(itemsAsuntos());
        asuntoField.setPromptText("Seleccione un asunto de la lista");
        juzgadoField.setItems(itemsJuzgados());
        juzgadoField.setPromptText("Seleccione un juzgado de la lista");
        fecEntradaColField.setPromptText("dd/mm/aaaa");
        
        turnoField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!ValidationsUtil.isCadenaVacia(anyoField.getText())) {
                    turnoField.setText(recuperaSiguienteTurno(anyoField.getText()));
                }
            }
        });

        // control
        identificadorField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                postDocumento();
                if (!ValidationsUtil.isCadenaVacia(ValidationsUtil.validaDocumento(identificadorField.getText(), (TdocumentoDTO) tipoIdentificadorField.getValue()))) {
                    if (tipoIdentificadorField.getValue() == null) {
                        Dialogs.create()
                                .title("Error validación")
                                .masthead("Error en el tipo de documento")
                                .message("Por favor introduzca un tipo de documento")
                                .showError();
                    } else {
                        Dialogs.create()
                                .title("Error en el documento")
                                .masthead("Hay errores en algunos campos")
                                .message(ValidationsUtil.validaDocumento(identificadorField.getText(), (TdocumentoDTO) tipoIdentificadorField.getValue())
                                        + "\nPor favor introduzca un " + tipoIdentificadorField.getValue() + " válido.")
                                .showError();
                    }
                    //identificadorField.requestFocus();
                    // Hacer que el foco se quede en el campo, si hay error
                } else {
                    expdte.setSolicitante(recuperaPersona((TdocumentoDTO) tipoIdentificadorField.getValue(), identificadorField.getText()));
                    rellenaDatosPersonales(expdte.getSolicitante());
                }
            }
        });
        
        fecEntradaColField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                if (!DateUtil.validDate(fecEntradaColField.getText())) {
                    Dialogs.create()
                                .title("Error validación")
                                .masthead("Error en la fecha de entrada al colegio")
                                .message("Por favor introduzca una fecha válida en el formato dd/mm/aaaa")
                                .showError();
                    fecEntradaColField.requestFocus();
                    // Hacer que el foco se quede en el campo, si hay error
                } 
            }
        });
    }

    /**
     * Sets the stage of this dialog.
     *
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
        /* comprobamos que editamos un expediente existente */
        if (expdte != null && expdte.getId() > 0) {
            anyoField.setText(Integer.toString(expdte.getAnyo()));
            turnoField.setText(Integer.toString(expdte.getNumTurno()));
            if (expdte.getNumExped() > 0) {
                expedField.setText(Integer.toString(expdte.getNumExped()));
            } else {
                expedField.setText("");
            }
            tipoIdentificadorField.setValue(expdte.getSolicitante().getTipoIdentificador());
            identificadorField.setText(expdte.getSolicitante().getIdentificador());
            rellenaDatosPersonales(expdte.getSolicitante());
            juzgadoField.setValue(expdte.getJuzgado());
            asuntoField.setValue(expdte.getAsunto());
            fecEntradaColField.setText(DateUtil.formatSort(expdte.getFecEntradaCol()));
            fecEntradaColField.setPromptText("dd/mm/yyyy");
        } else {
            /* Es un nuevo expediente */
            turnoField.setText(String.valueOf(expdte.getNumTurno()));
        }
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
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
        int codGuarda = -1;
        if (isInputValid()) {
            expdte.setAnyo(Integer.parseInt(anyoField.getText()));
            expdte.setNumTurno(Integer.parseInt(turnoField.getText()));
            if (!util.ValidationsUtil.isCadenaVacia(expedField.getText())) {
                expdte.setNumExped(Integer.parseInt(expedField.getText()));
            }
            // el solicitante se añade al seleccionarlo
            // expdte.setSolicitante(solic);
            expdte.setAsunto((TasuntoDTO) asuntoField.getValue());
            expdte.setJuzgado((JuzgadoDTO) juzgadoField.getValue());
            expdte.setFecEntradaCol(DateUtil.parseTime(fecEntradaColField.getText() + " 00:00:00"));

            Expediente exped = new Expediente();
            if (expdte.getId() > 0 ) {
                // Modificando un expediente
                codGuarda = exped.guardaExpediente(expdte);
            } else {
                // expediente nuevo
                codGuarda = exped.guardaNewExpediente(expdte);
            }
            if (codGuarda < 0) {
                Dialogs.create()
                        .title("Error guardando expediente")
                        .masthead("Se ha producido un error al guardar los datos del expediente.")
                        .message("Compruebe los archivos de Log para ver el error")
                        .showError();
            } else {
                okClicked = true;
                dialogStage.close();
            }
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    private PersonaDTO handleNewPerson() {
        PersonaDTO person = new PersonaDTO(-1, null, null, null, (TdocumentoDTO)tipoIdentificadorField.getValue(), identificadorField.getText());
        boolean okClicked = showPersonEditDialog(person);
        if (okClicked) {
                return person;
        }
        return null;
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (expdte.getSolicitante() == null || ValidationsUtil.isCadenaVacia(identificadorField.getText()) ) {
            errorMessage += "Los datos del solicitante están vacios!\n";
        }  
        if (ValidationsUtil.isCadenaVacia(anyoField.getText())) {
            errorMessage += "El año del expediente está vacío!\n";
        } else if (DateUtil.validYear(anyoField.getText()) ) {
            errorMessage += "El año del expediente no es válido!\n"; 
            
        } else if (Integer.parseInt(anyoField.getText()) > Integer.parseInt(DateUtil.nowYear())) {
            errorMessage += "El año del expediente no puede ser mayor que en año actual!\n"; 
        }
        if (ValidationsUtil.isCadenaVacia(anyoField.getText())) {
            errorMessage += "El año del expediente está vacío!\n";
        }
        if (ValidationsUtil.isCadenaVacia(identificadorField.getText())) {
            errorMessage += "El documento identificador está vacío!\n";
        } else {
            if (!ValidationsUtil.isCadenaVacia(ValidationsUtil.validaDocumento(identificadorField.getText(), (TdocumentoDTO) tipoIdentificadorField.getValue()))) {
                errorMessage += ValidationsUtil.validaDocumento(identificadorField.getText(), (TdocumentoDTO) tipoIdentificadorField.getValue()) + "!\n";
            }
        }
        if (juzgadoField.getValue() == null  ) {
            errorMessage += "El juzgado está vacío!\n";
        }
        if (asuntoField.getValue() == null  ) {
            errorMessage += "El asunto está vacío!\n";
        }
        
        if (fecEntradaColField.getText() == null || fecEntradaColField.getText().length() == 0) {
            errorMessage += "Fecha de entrada no válida!\n";
        } else if (!DateUtil.validDate(fecEntradaColField.getText())) {
            errorMessage += "Fecha de entrada no válida. Formato dd/mm/yyyy!\n";
        } else if (!DateUtil.parse(fecEntradaColField.getText()).isEqual(DateUtil.now()) && DateUtil.parse(fecEntradaColField.getText()).isAfter(DateUtil.now()) ){
            errorMessage += "Fecha de entrada no válida. No puede ser mayor al día actual. !\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Dialogs.create()
                    .title("Error en los campos")
                    .masthead("Hay errores en algunos campos")
                    .message(errorMessage + "\nPor favor corrija los errores")
                    .showError();
            return false;
        }
    }

    @FXML
    private void postDocumento() {
        ValidationsUtil valida = new ValidationsUtil();
        identificadorField.setText(valida.preparaDocumento(identificadorField.getText()));
    }

    /**
     * Lista de los tipos de Documento
     *
     * @return lista @TdocumentoDTO
     */
    private ObservableList<TdocumentoDTO> itemsTipoDocumento() {
        TdocumentoDao db = new TdocumentoDao();
        ObservableList<TdocumentoDTO> options = FXCollections.observableArrayList(db.listaTiposDocumento());
        return options;
    }

    /**
     * Lista de los asuntos
     *
     * @return lista @TasuntoDTO
     */
    private ObservableList<TasuntoDTO> itemsAsuntos() {
        TasuntoDao db = new TasuntoDao();
        ObservableList<TasuntoDTO> options = FXCollections.observableArrayList(db.listaTasuntos());
        return options;
    }
    
    private ObservableList<JuzgadoDTO> itemsJuzgados() {
        JuzgadoDao db = new JuzgadoDao();
        ObservableList<JuzgadoDTO> options = FXCollections.observableArrayList(db.listaJuzgados());
        return options;
    }

    private PersonaDTO recuperaPersona(TdocumentoDTO tipoDoc, String documento) {
        PersonaDao per = new PersonaDao();
        PersonaDTO solic = per.getPersonaByDocu(tipoDoc.getId(), documento);
        if (solic == null) {
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Persona no encontrada");
            alert.setHeaderText("El " + tipoDoc.getDescripcion() + " " + documento + " no está en la base de datos.");
            alert.setContentText("Si es correcto y es un nuevo solicitante pulse: Nuevo \nSi se ha equivocado de documento pulse: Cancelar");
            ButtonType buttonTypeNuevo = new ButtonType("Nuevo");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeNuevo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeNuevo){ 
                // programa nueva persona
                return handleNewPerson();
            } else {
                // Ha pulsado cancelar o cerrar
                
            }
            return null;
        } else {
            return solic;
        }
    }
    
    private void rellenaDatosPersonales(PersonaDTO solic) {
        if (solic != null) {
            solicitanteNombreLabel.setText(solic.toString());
            direccionLabel.setText(solic.getDireccion());
            codigoPostalLabel.setText(solic.getCodigoPostal() + "");
            localidadLabel.setText(solic.getLocalidad());
            provinciaLabel.setText(solic.getProvincia());
            telefonoLabel.setText(solic.getTelefono());
            movilLabel.setText(solic.getMovil());
        } else {
            solicitanteNombreLabel.setText("");
            direccionLabel.setText("");
            codigoPostalLabel.setText("");
            localidadLabel.setText("");
            provinciaLabel.setText("");
            telefonoLabel.setText("");
            movilLabel.setText("");
            identificadorField.setText("");
        }
    }
    
    private String recuperaSiguienteTurno(String anyo) {
        Expediente exped = new Expediente();
        return String.valueOf(exped.siguienteTurno(Integer.parseInt(anyo)));
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
            dialogStage.initOwner(this.dialogStage);
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
}
