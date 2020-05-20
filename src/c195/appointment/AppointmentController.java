/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.appointment;

import c195.C195;
import c195.dataprovider.Appointment;
import c195.dataprovider.Customer;
import c195.dataprovider.DataProvider;
import c195.util.Query;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class AppointmentController implements Initializable {

    @FXML private TableView appTable;
    @FXML private TableColumn custCol;
    @FXML private TableColumn userCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn descCol;
    @FXML private TableColumn locCol;
    @FXML private TableColumn conCol;
    @FXML private TableColumn typeCol;
    @FXML private TableColumn urlCol;
    @FXML private TableColumn startCol;
    @FXML private TableColumn endCol;
    @FXML private Button delBtn;
    @FXML private Button editBtn;
    @FXML private Button cancelBtn;
    @FXML private Button addBtn;
    
    private Appointment appointmentSelected;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            custCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            userCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            conCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            urlCol.setCellValueFactory(new PropertyValueFactory<>("url"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            ObservableList<Appointment> appointment = DataProvider.getAllAppointment();
            appTable.setItems(appointment);
        } catch (Exception e) {
            System.out.println("Error populating table: "+ e.getMessage());
        }
    }    

    @FXML
    private void clickOnDeleteBtn(ActionEvent event) throws Exception {
        appointmentSelected = (Appointment) appTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to delete the selected appointment??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Query.makeQuery("DELETE FROM appointment WHERE appointmentId = " + appointmentSelected.getAppointmentId()+"");
            System.out.println("Deleting...");
            ObservableList<Appointment> appointment = DataProvider.getAllAppointment();
            appTable.setItems(appointment);
        }      
        Alert notif = new Alert(Alert.AlertType.ERROR);
        notif.setHeaderText("Confirmation");
        notif.setContentText("Appointment deleted");
        notif.showAndWait();
    }

    @FXML
    private void clickOnEditBtn(ActionEvent event) {
        appointmentSelected = (Appointment) appTable.getSelectionModel().getSelectedItem();
        DataProvider.setAppointment(appointmentSelected);
        C195.closeWindow(editBtn);
        C195.openWindow("appointment/appointmentedit.fxml", "Edit Appointment");
    }

    @FXML
    private void clickOnCancelBtn(ActionEvent event) {
        C195.closeWindow(cancelBtn);
        C195.openWindow("mainview/MainView.fxml","Main View");
    }
    @FXML
    private void clickOnAddBtn(ActionEvent event) {
        System.out.println("Opening Add Appointment");
        C195.closeWindow(addBtn);
        C195.openWindow("appointment/appointmentadd.fxml", "Add Appointment");
    }
    
}
