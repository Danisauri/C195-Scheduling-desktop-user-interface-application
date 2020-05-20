/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195;

import c195.util.DBConnection;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;


/**
 *
 * @author danie
 */
public class C195 extends Application {
       
    public static void openWindow (String window, String windowTitle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(C195.class.getResource(window));
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Loading " + windowTitle + " Error: "+ e.getMessage());
        }
    }
    
    public static void closeWindow(Button button){
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    
    @Override
    
    public void start(Stage stage)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
            Scene loginScene = new Scene(root);
            stage.setScene(loginScene);
            stage.show();
        } catch (Exception e) {
            System.out.println("ERROR in Main1:" + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    
    public static void main(String[] args) throws Exception {
        try{
            DBConnection.startConnection();
            launch(args);
            DBConnection.closeConnection();
        }
        catch (Exception e){
            System.out.println("ERROR in Main2:" + e.getMessage());
        }
    }

}
