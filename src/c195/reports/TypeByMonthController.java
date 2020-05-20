/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.reports;

import c195.dataprovider.AppByType;
import c195.C195;
import c195.dataprovider.Appointment;
import c195.dataprovider.DataProvider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class TypeByMonthController implements Initializable {

    @FXML private TableView typeAppTbl;
    @FXML private TableColumn yearCol;
    @FXML private TableColumn monthCol;
    @FXML private TableColumn scrumCol;
    @FXML private TableColumn presCol;
    @FXML private TableColumn otherCol;
    @FXML private Button backBtn;

    public void backBtnPress(){
        C195.closeWindow(backBtn);
        C195.openWindow("mainview/MainView.fxml","Main View");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            ObservableList<AppByType> appointment = DataProvider.getAppByType();

            yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
            monthCol.setCellValueFactory((new PropertyValueFactory<>("month")));
            scrumCol.setCellValueFactory((new PropertyValueFactory<>("scrum")));
            presCol.setCellValueFactory((new PropertyValueFactory<>("presentation")));
            otherCol.setCellValueFactory((new PropertyValueFactory<>("other")));
            
            typeAppTbl.setItems(appointment);
        }
        catch (Exception e) {
            System.out.println("Error populating table on type by month: "+ e.getMessage());
        }
    }    
    
}
