/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.appointment;

import c195.C195;
import c195.dataprovider.Customer;
import c195.dataprovider.DataProvider;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class AppointmentAddController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
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

    @FXML
    public void clickOnSaveBtn() throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to save the changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Integer userIdFromUsername = DataProvider.getId.getIdMethod("userId", "user", "userName ='"+DataProvider.getUsername()+"\'");
            Integer custIdFromName = DataProvider.getId.getIdMethod("customerId", "customer", "customerName = \'"+custSlct.getSelectionModel().getSelectedItem()+"\'");
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
            Boolean validDate = DataProvider.checkOverlappingAppointment(dateSlct.getValue(), startToLocalTime, endToLocalTime);
            String appointmentStart = DataProvider.concatenateDate(dateSlct.getValue(), startToLocalTime);
            String appointmentEnd = DataProvider.concatenateDate(dateSlct.getValue(), endToLocalTime);
            if (validDate && validInfo){
                DataProvider.createField("appointment", 
                        "customerId, userId, title, description, location, contact, type, url, start, end", 
                        custIdFromName+", "+
                        userIdFromUsername+", \'"+
                        titleTxt.getText()+"\', \'"+
                        descripTxt.getText()+"\', \'"+
                        locTxt.getText()+"\', \'"+
                        conTxt.getText()+"\', \'"+
                        typeSlct.getSelectionModel().getSelectedItem()+"\', \'"+
                        urlTxt.getText()+"\', \'"+
                        appointmentStart+"\', \'"+
                        appointmentEnd+"\'");
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation Dialog");
                alert2.setContentText("New Appointment saved!");
                alert2.showAndWait();
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Confirmation Dialog");
                alert3.setContentText("Do you want to add another Appointment?");
                Optional<ButtonType> result3 = alert3.showAndWait();
                if (result3.get() == ButtonType.OK){
                    custSlct.getItems().clear();
                    typeSlct.getItems().clear();
                    dateSlct.setValue(null);
                    startSlct.getItems().clear();
                    endSlct.getItems().clear();
                    titleTxt.setText("");
                    descripTxt.setText("");
                    locTxt.setText("");
                    conTxt.setText("");
                    urlTxt.setText("");
                    custSlct.getItems().addAll(DataProvider.getAllElements("customerName", "customer", ""));
                    typeSlct.getItems().addAll(DataProvider.getAllDistinctElements("type","appointment", ""));
                    startSlct.getItems().addAll(DataProvider.hourOptions());
                    endSlct.getItems().addAll(DataProvider.hourOptions());
                }
                else{
                    clickOnCancelBtn();
                }
            }
        }
    }

    @FXML
    public void clickOnCancelBtn(){
        C195.closeWindow(cancelBtn);
        C195.openWindow("appointment/appointment.fxml", "Edit/delete Appointment");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            typeSlct.getItems().addAll(DataProvider.getAllDistinctElements("type", "appointment", ""));
            userTxt.setPromptText(DataProvider.getUsername());
            startSlct.getItems().addAll(DataProvider.hourOptions());
            endSlct.getItems().addAll(DataProvider.hourOptions());
            custSlct.getItems().addAll(DataProvider.getAllElements("customerName", "customer", ""));
        } catch (Exception e) {
            System.out.println("Error initializing appointmentAddController: "+ e.getMessage());
        }        
    }    
    
}
