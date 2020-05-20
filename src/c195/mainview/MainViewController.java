/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.mainview;

import c195.C195;
import c195.dataprovider.Appointment;
import c195.dataprovider.DataProvider;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author danie
 */
public class MainViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Button viewCustBtn;
    @FXML private Button viewAppBtn;
    @FXML private Button weekMonthBtn;
    @FXML private Button appTypeBtn;
    @FXML private Button schByUserBtn;
    @FXML private Button CustSchBtn;
    @FXML private Button dbLoginBtn;
    @FXML private Label welcomeLabel;
    
    @FXML
    private void viewCustBtnPress(){
        System.out.println("Opening customer");
        C195.closeWindow(viewCustBtn);
        C195.openWindow("customer/customer.fxml", "Edit/Delete Customer");
    }
  
    @FXML
    private void viewAppBtnPress() {
        System.out.println("Opening Appointment");
        C195.closeWindow(viewAppBtn);
        C195.openWindow("appointment/appointment.fxml", "Edit/delete Appointment");
    }
    
    @FXML
    private void weekMonthBtnPress() {
        System.out.println("Opening week/month view");
        C195.closeWindow(weekMonthBtn);
        C195.openWindow("appointment/appointmentfiltered.fxml", "Week/Month View");
    }

    @FXML
    private void appTypeBtnPress() {
        System.out.println("Opening Appt type by month view");
        C195.closeWindow(appTypeBtn);
        C195.openWindow("reports/typebymonth.fxml", "Appointment type by month View");
    }
         
    @FXML
    private void schByUserBtnPress() {
        System.out.println("Opening schedule by consultant view");
        C195.closeWindow(schByUserBtn);
        C195.openWindow("reports/schbyuser.fxml", "Schedule by Consultant View");
    }
    
    @FXML
    private void CustSchBtnPress() {
        System.out.println("Opening schedule by customer view");
        C195.closeWindow(CustSchBtn);
        C195.openWindow("reports/schbycustomer.fxml", "Schedule by Customer View");
        
    }
    
    @FXML
    private void dbLoginBtnPress() throws Exception {
        System.out.println("Opening DatabaseLog");
        DataProvider.openFile();
        
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Controller Initiated");
        welcomeLabel.setText("Welcome "+DataProvider.getUsername()+"!");
        try{
            ObservableList<Appointment> nextApp = DataProvider.checkNextAppointment(LocalDate.now(), LocalTime.now());
            if(nextApp.size()>0){
            String customerName = nextApp.get(0).getCustomerName();
            String appHour = nextApp.get(0).getStartHour();
            String appointment = customerName+" at "+appHour;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning!");
            alert.setContentText("You have an appointment scheduled in the next 15 minutes with "+appointment);
            alert.showAndWait();
            }
            else{
                System.out.println("No appointments in the next 15 minutes");
            }
        }
        catch(Exception e){
            System.out.println("Error checking appointments in the next 15 minutes: "+e.getMessage());
        }
    }    
    
}
