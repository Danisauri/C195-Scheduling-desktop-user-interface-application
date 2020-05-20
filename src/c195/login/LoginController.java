/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.login;

import c195.C195;
import c195.dataprovider.*;
import c195.util.Query;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


/**
 *
 * @author danie
 */
public class LoginController implements Initializable {
    
    @FXML private TextField usernameFieldLogin;
    @FXML private PasswordField passwordFieldLogin;
    @FXML private Label schedulerlabel;
    @FXML private Label usernamelabel;
    @FXML private Label passwordlabel;
    @FXML private Button cancelButtonLogin;
    @FXML private Button acceptButtonLogin;
    private String querystatus;

    
    @FXML
    private void cancelButtonLogin() {
        Platform.exit();
    }
    @FXML
    private void acceptButtonLogin() throws Exception {
        String usernameInput = usernameFieldLogin.getText();
        String passwordInput = passwordFieldLogin.getText();
        System.out.println("Searching user");
        String myQuery = "SELECT * FROM user WHERE userName = \'"+ usernameInput+"\'" + "AND password = \'" + passwordInput + "\'";
        ObservableList<String> result = Query.executeQueryToList(myQuery, rs -> rs.getString("username"));
        if (result.size()>0){
            DataProvider.setUsername(usernameInput);
            System.out.println("User and password accepted!");
            C195.closeWindow(acceptButtonLogin);
            C195.openWindow("mainview/MainView.fxml","Main View");
            querystatus = usernameInput + " Success";
            
        }
        else{
            System.out.println("User or password wrong!");
            ResourceBundle rb = ResourceBundle.getBundle("c195.util.lang/Nat", Locale.getDefault());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(rb.getString("userPasswordIncorrect"));
            alert.setContentText(rb.getString("tryAgain"));
            alert.showAndWait();
            querystatus = usernameInput + " Failure";
        }
        DataProvider.manipulateFile(querystatus);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("c195.util.lang/Nat", Locale.getDefault());
        usernameFieldLogin.promptTextProperty().set(rb.getString("enterUsername"));
        passwordFieldLogin.promptTextProperty().set(rb.getString("enterPassword"));
        schedulerlabel.setText(rb.getString("scheduler"));
        usernamelabel.setText(rb.getString("username"));
        passwordlabel.setText(rb.getString("password"));
        cancelButtonLogin.setText(rb.getString("cancel"));
        acceptButtonLogin.setText(rb.getString("accept"));
    }    
    
}
