/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.customer;

import c195.C195;
import c195.dataprovider.DataProvider;
import c195.util.Query;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class CustomeraddController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField nameTxt;
    @FXML private TextField addressTxt;
    @FXML private TextField address2Txt;
    @FXML private ComboBox citySlct;
    @FXML private ComboBox countrySlct;
    @FXML private TextField postcodeTxt;
    @FXML private TextField phoneTxt;
    @FXML private Button cancelBtn;

    

    
    @FXML
    public void clickOnCountrySlct () throws Exception{
        citySlct.getItems().clear();
        citySlct.setPromptText("");
        Integer countryIdFromSelectedCountry = DataProvider.getId.getIdMethod("countryId","country", "country = \'"+countrySlct.getSelectionModel().getSelectedItem().toString()+"\'");
        citySlct.getItems().addAll(DataProvider.getAllElements("city", "city", "countryId = "+countryIdFromSelectedCountry));
    }
    @FXML
    public void clickOnSaveAddBtn() throws Exception{
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
                Integer cityIdFromCitySelected = DataProvider.getId.getIdMethod("cityId", "city", "city = \'"+city+"\'");
                DataProvider.createField ("address", "address, address2, cityId, postalCode, phone", 
                        "\'"+addressTxt.getText()+"\', "+
                        "\'"+address2Txt.getText()+"\', "+
                        cityIdFromCitySelected+", "+
                        "\'"+postcodeTxt.getText()+"\', "+
                        "\'"+phoneTxt.getText()+"\'");
                Integer idOfNewAddress = DataProvider.getId.getIdMethod("addressId", "address", "address = \'"+addressTxt.getText()+"\'");
                Integer active = 1;
                DataProvider.createField ("customer", "customerName, addressId, active",
                        "\'"+nameTxt.getText()+"\', "+
                        idOfNewAddress.toString()+", "+
                        active);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation Dialog");
                alert2.setContentText("Changes saved!");
                alert2.showAndWait();
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Confirmation Dialog");
                alert3.setContentText("Do you want to add another customer?");
                Optional<ButtonType> result3 = alert3.showAndWait();
                if (result3.get() == ButtonType.OK){
                    citySlct.getItems().clear();
                    countrySlct.getItems().clear();
                    nameTxt.setText("");
                    addressTxt.setText("");
                    address2Txt.setText("");
                    citySlct.setPromptText("Please select a country");
                    countrySlct.getItems().addAll(DataProvider.getAllElements("country","country", ""));;
                    postcodeTxt.setText("");
                    phoneTxt.setText("");
                }
                else{
                    clickOnCancelBtn();
                }
            }
        }
    }
    
    @FXML
    public void clickOnCancelBtn (){
        C195.closeWindow(cancelBtn);
        C195.openWindow("customer/customer.fxml", "Edit/Delete Customer");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        try {
            countrySlct.getItems().addAll(DataProvider.getAllElements("country", "country", ""));
        } catch (Exception e) {
            System.out.println("Error getting countries: "+e);
        }
    }    
    
}
