/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.reports;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class SchByCustomerController implements Initializable {

    @FXML private ComboBox customerSlct;
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
    @FXML private Button backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            customerSlct.getItems().addAll(DataProvider.getAllDistinctElements("customerName", "customer", ""));
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
        } catch (Exception e) {
            System.out.println("Error populating table: "+ e.getMessage());
        }
    }    

    @FXML
    private void consultSlctPress(ActionEvent event) throws Exception {
        String customerelectedName  = customerSlct.getSelectionModel().getSelectedItem().toString();
        Integer customerIdFromName = DataProvider.getId.getIdMethod("customerId", "customer", "customerName = \'"+customerelectedName+"\'");
        ObservableList<Appointment> newAppointments = DataProvider.filterUserAppBySelection("customerId ="+customerIdFromName);
        appTable.setItems(newAppointments);
    }

    @FXML
    private void backBtnPress(ActionEvent event) {
        C195.closeWindow(backBtn);
        C195.openWindow("mainview/MainView.fxml","Main View");
    }
    
}
