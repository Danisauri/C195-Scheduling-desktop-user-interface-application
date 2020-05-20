/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.util;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author danie
 */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U06k7G";
    
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    
    //Driver and Connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    //Username
    private static final String username = "U06k7G";
    
    //Password
    private static final String password = "53688791510";
    
    public static void startConnection() throws Exception{
        Class.forName(MYSQLJDBCDriver);
        conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connection succesful!");
    }
    public static void closeConnection() throws Exception{
        conn.close();
        System.out.println("Connection Closed!");
    }
}
