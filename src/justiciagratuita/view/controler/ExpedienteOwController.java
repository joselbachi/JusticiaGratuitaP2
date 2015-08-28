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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Dialogs;
import javafx.scene.control.cell.PropertyValueFactory;
import justiciagratuita.JusticiaGratuita;
import justiciagratuita.modelo.PersonaDTO;
import util.CalendarUtil;
/**
 *
 * @author joseluis.bachiller
 */
public class ExpedienteOwController {
    @FXML
    private TableView<PersonaDTO> personTable;
    @FXML
    private TableColumn<PersonaDTO, String> firstNameColumn;
    @FXML
    private TableColumn<PersonaDTO, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

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
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<PersonaDTO, String>("nombre"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<PersonaDTO, String>("pApellido"));

        // Auto resize columns
        personTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // clear person
        showPersonDetails(null);

        // Listen for selection changes
        personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PersonaDTO>() {
            @Override
            public void changed(ObservableValue<? extends PersonaDTO> observable,
                    PersonaDTO oldValue, PersonaDTO newValue) {
                showPersonDetails(newValue);
            }
        });
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
    private void showPersonDetails(PersonaDTO person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getNombre());
            lastNameLabel.setText(person.getsApellido());
            
            streetLabel.setText(person.getDireccion());
            postalCodeLabel.setText(Integer.toString(person.getCodigoPostal()));
            cityLabel.setText(person.getProvincia());
            birthdayLabel.setText(CalendarUtil.format(person.getBirthday()));

        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            Dialogs.showErrorDialog(mainApp.getPrimaryStage(),
                                "Por favor, seleccione el expediente que desea borrar", 
                                "No hay ningún expediente seleccionado", 
                                "Error");
                    
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        PersonaDTO tempPerson = new PersonaDTO();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        PersonaDTO selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                refreshPersonTable();
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected
            Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
                    "Please select a person in the table.",
                    "No Person Selected", "No Selection");
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
