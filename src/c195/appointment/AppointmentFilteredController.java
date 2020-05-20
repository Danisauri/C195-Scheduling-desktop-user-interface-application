/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.appointment;

import c195.C195;
import c195.dataprovider.Appointment;
import c195.dataprovider.DataProvider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class AppointmentFilteredController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private RadioButton weekBtn;
    @FXML private ToggleGroup group;
    @FXML private RadioButton monthBtn;
    @FXML private TableView<Appointment> appTable;
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
    @FXML private Button cancelBtn;

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
            ObservableList<Appointment> appointment = DataProvider.getFilteredAppointment("week");
            appTable.setItems(appointment);
            weekBtn.selectedProperty().set(true);
        } catch (Exception e) {
            System.out.println("Error populating table: "+ e.getMessage());
        }
    }    

    @FXML
    private void clickOnCancelBtn(ActionEvent event) {
        C195.closeWindow(cancelBtn);
        C195.openWindow("mainview/MainView.fxml","Main View");
    }
    
    @FXML
    private void weekBtnPress() throws Exception{
        ObservableList<Appointment> appointment = DataProvider.getFilteredAppointment("week");
        appTable.setItems(appointment);
    }
    
    @FXML
    private void monthBtnPress() throws Exception{
        ObservableList<Appointment> appointment = DataProvider.getFilteredAppointment("month");
        appTable.setItems(appointment);
    }
    
    @FXML
    private void allBtnPress() throws Exception{
        ObservableList<Appointment> appointment = DataProvider.getAllAppointment();
        appTable.setItems(appointment);
    }
    
}
