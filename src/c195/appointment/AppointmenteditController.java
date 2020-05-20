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
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class AppointmenteditController implements Initializable {


    @FXML private ComboBox custSlct;
    @FXML private TextField titleTxt;
    @FXML private TextField descripTxt;
    @FXML private TextField locTxt;
    @FXML private TextField conTxt;
    @FXML private ComboBox typeSlct;
    @FXML private TextField urlTxt;
    @FXML private DatePicker dateSlct;
    @FXML private ComboBox startSlct;
    @FXML private ComboBox endSlct;
    @FXML private TextField userTxt;
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;
    
    private Appointment appointmentSelected;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            typeSlct.getItems().addAll(DataProvider.getAllDistinctElements("type", "appointment", ""));
            userTxt.setPromptText(DataProvider.getUsername());
            startSlct.getItems().addAll(DataProvider.hourOptions());
            endSlct.getItems().addAll(DataProvider.hourOptions());
            custSlct.getItems().addAll(DataProvider.getAllElements("customerName", "customer", ""));
            
            appointmentSelected = DataProvider.getAppointment();

            custSlct.setValue(appointmentSelected.getCustomerName());
            titleTxt.setText(appointmentSelected.getTitle());
            descripTxt.setText(appointmentSelected.getDescription());
            locTxt.setText(appointmentSelected.getLocation());
            conTxt.setText(appointmentSelected.getContact());
            typeSlct.setValue(appointmentSelected.getType());
            urlTxt.setText(appointmentSelected.getUrl());
            dateSlct.setValue(appointmentSelected.getStart().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toLocalDate());
            startSlct.setValue(appointmentSelected.getStartHour());
            endSlct.setValue(appointmentSelected.getEndHour());

        } catch (Exception e) {
            System.out.println("Error populating table on Edit Appointment: "+ e.getMessage());
        }
    }    


    @FXML
    private void clickOnSaveBtn(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to save the changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Integer userIdFromUsername = DataProvider.getId.getIdMethod("userId", "user", "userName ='"+DataProvider.getUsername()+"\'");
            
            String dateOption = "";

            LocalTime startToLocalTime = LocalTime.of(0, 0);
            LocalTime endToLocalTime = LocalTime.of(0, 0);

            if(startSlct.getSelectionModel().getSelectedIndex() != -1){
                startToLocalTime = LocalTime.parse(startSlct.getValue().toString());
            };
            if(endSlct.getSelectionModel().getSelectedIndex() != -1){
                endToLocalTime = LocalTime.parse(endSlct.getValue().toString());
            };
            if(dateSlct.getValue() != null){
                dateOption = dateSlct.getValue().toString();
            };
            Boolean validInfo = DataProvider.checkFieldsAppointment(custSlct.getSelectionModel().getSelectedIndex(), typeSlct.getSelectionModel().getSelectedIndex(), dateOption, startToLocalTime, endToLocalTime);
            Boolean validDate = DataProvider.checkOverlappingAppointment(dateSlct.getValue(), startToLocalTime, endToLocalTime, appointmentSelected);
            String start = DataProvider.concatenateDate(dateSlct.getValue(), startToLocalTime);
            String end = DataProvider.concatenateDate(dateSlct.getValue(), endToLocalTime);
            if (validDate && validInfo){
                DataProvider.updateField("appointment", "customerId", ""+appointmentSelected.getCustomerId(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "userId", userIdFromUsername.toString(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "title", titleTxt.getText(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "description", descripTxt.getText(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "location", locTxt.getText(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "contact", conTxt.getText(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "type", typeSlct.getSelectionModel().getSelectedItem().toString(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "url", urlTxt.getText(), " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "start", start, " appointmentId = "+appointmentSelected.getAppointmentId());
                DataProvider.updateField("appointment", "end", end, " appointmentId = "+appointmentSelected.getAppointmentId());

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation Dialog");
                alert2.setContentText("Changes saved!");
                alert2.showAndWait();
                C195.closeWindow(saveBtn);
                C195.openWindow("appointment/appointment.fxml", "Edit/delete Appointment");
            }
        }
    }

    @FXML
    private void clickOnCancelBtn() {
        C195.closeWindow(cancelBtn);
        C195.openWindow("appointment/appointment.fxml", "Edit/delete Appointment");
    }
    
}
