/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.customer;

import c195.C195;
import c195.dataprovider.*;
import c195.util.Query;
import java.net.URL;
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

public class CustomerController implements Initializable {
    
    @FXML private TableView customerTable;
    @FXML private TableColumn idColumn;
    @FXML private TableColumn nameColumn;
    @FXML private TextField nameTxt;
    @FXML private TextField addressTxt;
    @FXML private TextField address2Txt;
    @FXML private ComboBox citySlct;
    @FXML private ComboBox countrySlct;
    @FXML private TextField postcodeTxt;
    @FXML private TextField phoneTxt;
    @FXML private Button delBtn;
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;
    @FXML private Button addBtn;
    
    private Customer customerSelected;
    


        
    @FXML
    public void clickOnCustomer () throws Exception{
        customerSelected = (Customer) customerTable.getSelectionModel().getSelectedItem();
        
        Integer cityIdFromAddressId = DataProvider.getId.getIdMethod("cityId", "address", "addressId = " + customerSelected.getAddressId());
        Integer countryIdFromAddressId = DataProvider.getId.getIdMethod("countryId", "city", "cityId = "+cityIdFromAddressId);
        
        countrySlct.getItems().clear();
        citySlct.getItems().clear();
        countrySlct.getItems().addAll(DataProvider.getAllElements("country", "country", ""));
        citySlct.getItems().addAll(DataProvider.getAllElements("city", "city", "countryId = "+countryIdFromAddressId));
        
        nameTxt.setText(customerSelected.getCustomerName());
        addressTxt.setText(DataProvider.getTextFromDB("address","address", "addressId = \'" + customerSelected.getAddressId()+"\'"));
        address2Txt.setText(DataProvider.getTextFromDB("address2","address", "addressId = \'" + customerSelected.getAddressId()+"\'"));
        
        
        citySlct.setPromptText(DataProvider.getTextFromDB("city", "city", "cityID = " + cityIdFromAddressId ));
        countrySlct.setValue(DataProvider.getTextFromDB("country", "country", "countryId = " + countryIdFromAddressId));
        
        postcodeTxt.setText(DataProvider.getTextFromDB("postalCode", "address", "addressId = \'" + customerSelected.getAddressId()+"\'"));
        phoneTxt.setText(DataProvider.getTextFromDB("phone", "address", "addressId = \'" + customerSelected.getAddressId()+"\'"));
        
        
        
    }
    
    @FXML
    public void clickOnCountrySlct () throws Exception{
        citySlct.getItems().clear();
        Integer countryIdFromSelectedCountry = DataProvider.getId.getIdMethod("countryId","country", "country = \'"+countrySlct.getSelectionModel().getSelectedItem().toString()+"\'");
        citySlct.getItems().addAll(DataProvider.getAllElements("city", "city", "countryId = "+countryIdFromSelectedCountry));
    }
    
    @FXML
    public void clickOnDeleteBtn() throws Exception{
        customerSelected = (Customer) customerTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to delete the customer "+ customerSelected.getCustomerName() + "??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Query.makeQuery("DELETE FROM customer WHERE customerId = " + customerSelected.getCustomerId()+"");
            System.out.println("Deleting...");
            nameTxt.setText("");
            addressTxt.setText("");
            address2Txt.setText("");
            citySlct.getSelectionModel().clearSelection();
            citySlct.getItems().clear();
            countrySlct.getSelectionModel().clearSelection();
            countrySlct.getItems().clear();
            postcodeTxt.setText("");
            phoneTxt.setText("");
            ObservableList<Customer> customers = DataProvider.getAllCustomer();
            customerTable.setItems(customers);
        }      
        Alert notif = new Alert(Alert.AlertType.ERROR);
        notif.setHeaderText("Confirmation");
        notif.setContentText("Customer deleted");
        notif.showAndWait();
    }
        
    @FXML
    public void clickOnSaveBtn() throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to save the changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String city = "";
            String country = "";
            if(countrySlct.getSelectionModel().getSelectedIndex() != -1){
                country = "valid";
            }
            if(citySlct.getSelectionModel().getSelectedIndex() != -1){
                city = citySlct.getSelectionModel().getSelectedItem().toString();
            }
            Boolean validInfo = DataProvider.checkFieldsCustomer(nameTxt.getText(), addressTxt.getText(), country, city, postcodeTxt.getText(), phoneTxt.getText());
            if(validInfo){
                DataProvider.updateField ("customer", "customerName", nameTxt.getText(), "customerId = \'"+customerSelected.getCustomerId() + "\'");
                DataProvider.updateField ("address", "address", addressTxt.getText(), "addressId = \'" + customerSelected.getAddressId() + "\'");
                DataProvider.updateField ("address", "address2", address2Txt.getText(), "addressId = \'" + customerSelected.getAddressId() + "\'");
                DataProvider.updateField ("address", "postalCode", postcodeTxt.getText(), "addressId = \'" + customerSelected.getAddressId() + "\'");
                DataProvider.updateField ("address", "phone", phoneTxt.getText(), "addressId = \'" + customerSelected.getAddressId() + "\'");
                Integer countryidFromCountrySlct = DataProvider.getId.getIdMethod("countryId", "country", "\"country =\\'\"+countrySlct.getSelectionModel().getSelectedItem().toString()+\"\\'\"");
                Integer cityIdFromCountryId = DataProvider.getId.getIdMethod("cityId", "city", "countryId = "+countryidFromCountrySlct);
                DataProvider.updateField ("address", "cityId", cityIdFromCountryId.toString(), "addressId = \'" + customerSelected.getAddressId() + "\'" );

                ObservableList<Customer> customers = DataProvider.getAllCustomer();
                customerTable.setItems(customers);
                Alert notif = new Alert(Alert.AlertType.ERROR);
                notif.setHeaderText("Confirmation");
                notif.setContentText("Changes saved");
                notif.showAndWait();
        }
        }
    }
    
    @FXML
    public void clickOnCancelBtn (){
        C195.closeWindow(cancelBtn);
        C195.openWindow("mainview/MainView.fxml","Main View");
    }
    @FXML
    public void clickOnAddBtn (){
        System.out.println("Opening add Customer");
        C195.closeWindow(addBtn);
        C195.openWindow("customer/customeradd.fxml", "Add Customer");
    }

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            ObservableList<Customer> customers = DataProvider.getAllCustomer();
            customerTable.setItems(customers);
        } catch (Exception e) {
            System.out.println("Error populating table: "+ e.getMessage());
        }
    }    
    
}
