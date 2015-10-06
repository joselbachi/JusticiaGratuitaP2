/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.view.controler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import justiciagratuita.JusticiaGratuita;
import justiciagratuita.modelo.ExpedienteDTO;
import justiciagratuita.modelo.logica.Expediente;
import org.controlsfx.dialog.Dialogs;
import util.DateUtil;
/**
 *
 * @author joseluis.bachiller
 */
public class ExpedienteOwController {
    @FXML
    private TableView<ExpedienteDTO> personTable;
    @FXML
    private TableColumn<ExpedienteDTO, String> firstNameColumn;
    @FXML
    private TableColumn<ExpedienteDTO, String> lastNameColumn;
    @FXML
    private TableColumn<ExpedienteDTO, String> numExpteColumn;

    @FXML
    private Label solicitanteNombreField;
    @FXML
    private Label documentoSolicField;
    @FXML
    private Label juzgadoField;
    @FXML
    private Label letradoNombreField;
    @FXML
    private Label procuradorNombreField;
    @FXML
    private Label fecEntradaField;
    @FXML
    private Label asuntoField;
    @FXML
    private Label numExpedienteField;
    @FXML
    private Label numTurnoField;
    @FXML
    private Label estadoField;
    
    // Reference to the main application.
    private JusticiaGratuita mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ExpedienteOwController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        // Initialize the person table with the two columns. Para Java 7
        //firstNameColumn.setCellValueFactory(new PropertyValueFactory<PersonaDTO, String>("nombre"));
        //lastNameColumn.setCellValueFactory(new PropertyValueFactory<PersonaDTO, String>("pApellido"));
        // Initialize the person table with the two columns. Para Java 8
        firstNameColumn.setCellValueFactory(
            cellData -> cellData.getValue().solicitanteNombreProperty());
        lastNameColumn.setCellValueFactory(
            cellData -> cellData.getValue().solicitanteNombreProperty());
        numExpteColumn.setCellValueFactory(
            cellData -> cellData.getValue().numExpedienteProperty());
        

        // Auto resize columns
        personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // clear expediente
        showExpedienteDetails(null);

        // Listen for selection changes. Para Java 7
        //personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PersonaDTO>() {
        //    @Override
        //    public void changed(ObservableValue<? extends PersonaDTO> observable,
        //            PersonaDTO oldValue, PersonaDTO newValue) {
        //        showPersonDetails(newValue);
        //    }
        //});
        
        // Listen for selection changes and show the person details when changed. Para Java 8
        personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showExpedienteDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(JusticiaGratuita mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
    
    /**
     * Fills all text fields to show details about the person. If the specified
     * person is null, all text fields are cleared.
     *
     * @param person the person or null
     */ 
    private void showExpedienteDetails(ExpedienteDTO expediente) {
        if (expediente != null) {
            // Fill the labels with info from the person object.
            Expediente exped = new Expediente();
            expediente = exped.recuperaDatosExpedi(expediente);
            solicitanteNombreField.setText(expediente.getSolicitanteNombre());
            documentoSolicField.setText(expediente.getDocumentoSolic());
            juzgadoField.setText(expediente.getJuzgadoNombre());
            if (expediente.getLetrado() != null) {
                letradoNombreField.setText(expediente.getLetrado().toString());
            } else {
                letradoNombreField.setText("");
            }
            if (expediente.getProcurador() != null) {
                procuradorNombreField.setText(expediente.getProcurador().toString());
            } else {
                procuradorNombreField.setText("");
            }
            fecEntradaField.setText(DateUtil.format(expediente.getFecEntradaCol()));
            numExpedienteField.setText(String.valueOf(expediente.getNumExpediente()));
            numTurnoField.setText(String.valueOf(expediente.getNumTurnoComp()));
            asuntoField.setText(expediente.getAsunto().toString());
            estadoField.setText(expediente.getEstado().getDescripcion());
           
            
        } else {
            // Expediente is null, remove all the text.

            solicitanteNombreField.setText("");
            documentoSolicField.setText("");
            juzgadoField.setText("");
            letradoNombreField.setText("");
            procuradorNombreField.setText("");
            fecEntradaField.setText("");
            asuntoField.setText("");
            numExpedienteField.setText("");
            numTurnoField.setText("");
            estadoField.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteExpdte() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Para java 7 con javafx-dialogs-0.0.4
            //Dialogs.showErrorDialog(mainApp.getPrimaryStage(),
            //                    "Por favor, seleccione el expediente que desea borrar", 
            //                    "No hay ningún expediente seleccionado", 
            //                    "Error");
            // Nothing selected.
        Dialogs.create()
            .title("Nada seleccionado")
            .masthead("No ha seleccionado ningún expediente.")
            .message("Seleccione un expediente de la tabla.")
            .showWarning();
                    
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewExpte() {
        ExpedienteDTO newExped = new ExpedienteDTO();
        boolean okClicked = mainApp.newExpedienteEditDialog(newExped);
        if (okClicked) {
            mainApp.getPersonData().add(newExped);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditExpte() {
        ExpedienteDTO selectedExped = personTable.getSelectionModel().getSelectedItem();
        Expediente exped = new Expediente();
        selectedExped = exped.recuperaDatosExpedi(selectedExped);
        if (selectedExped != null) {
            boolean okClicked = mainApp.showExpedienteEditDialog(selectedExped);
            if (okClicked) {
                refreshPersonTable();
                showExpedienteDetails(selectedExped);
            }

        } else {
            // Nothing selected
            //Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
            //        "Seleccione un expediente de la tabla.",
            //        "No ha seleccionado ningún expediente", "Nada seleccionado");
            Dialogs.create()
                .title("Nada seleccionado")
                .masthead("No ha seleccionado ningún expediente.")
                .message("Seleccione un expediente de la tabla.")
                .showWarning();
        }
    }

    /**
     * Refreshes the table. This is only necessary if an item that is already in
     * the table is changed. New and deleted items are refreshed automatically.
     *
     * This is a workaround because otherwise we would need to use property
     * bindings in the model class and add a *property() method for each
     * property. Maybe this will not be necessary in future versions of JavaFX
     * (see http://javafx-jira.kenai.com/browse/RT-22599)
     */
    private void refreshPersonTable() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        personTable.setItems(null);
        personTable.layout();
        personTable.setItems(mainApp.getPersonData());
        // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
        personTable.getSelectionModel().select(selectedIndex);
    }
    


}
