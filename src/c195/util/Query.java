/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.util;

import static c195.util.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author danie
 */
public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    public static void makeQuery (String query) throws Exception{
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
    }
    
    public static ObservableList executeQueryToList (String query, CreateObject fn){        
        try{

            Statement stmt = conn.createStatement();

            ObservableList<Object> list = FXCollections.observableArrayList();
            ResultSet result =  stmt.executeQuery(query);
            while(result.next()){
                list.add(fn.objeto(result));
            }
            return list;
        }
        catch(Exception e){
            System.out.println("Error in makeQuery: "+e.getMessage());
        }
        return FXCollections.observableArrayList();
    }
    public interface CreateObject{
        Object objeto (ResultSet rs) throws Exception;
    }
}
