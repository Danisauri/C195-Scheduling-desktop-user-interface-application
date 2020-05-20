/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.dataprovider;

import c195.util.Query;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author danie
 */
public class DataProvider {
    
    private static ObservableList<Customer> allCustomer;
    private static ObservableList<Appointment> allAppointment;
    private static ObservableList<AppByType> allAppByType;
    private static String username;
    private static Appointment appointment;
    private static File file = new File("c://temp//logFile.txt");
    
    // LAMBDA EXPRESSION: a lambda expression that simplify getting the int ID from any table where a specific condition is met
    public static getId getId = (selectWhosId, fromWhichTable, WhereCondition) -> ((Integer)Query.executeQueryToList("SELECT "+selectWhosId+" FROM "+fromWhichTable+" WHERE "+WhereCondition, rs -> rs.getInt(selectWhosId)).get(0));
    
    public interface getId {
    int getIdMethod(String selectWhosId, String fromWhichTable, String WhereCondition);
    }
    
    //methods for get/set username in use
    public static void setUsername (String username){
        DataProvider.username = username;
    }
    
    public static String getUsername (){
        return username;
    }
    
    //methods for get/set appointment in use
    public static void setAppointment (Appointment appointment){
        DataProvider.appointment = appointment;
    }
    
    public static Appointment getAppointment (){
        return appointment;
    }
    
    //methods for Time management
    public static ObservableList<String> hourOptions (){
        ObservableList<String> hourOptions = 
        FXCollections.observableArrayList(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00"
        );
        return hourOptions;     
    }
    
    public static LocalDateTime timeZoneConverter(ResultSet rs, String column) throws Exception{
        int day = rs.getDate(column).toLocalDate().getDayOfMonth();
        Month month = rs.getDate(column).toLocalDate().getMonth();
        int year = rs.getDate(column).toLocalDate().getYear();
        int hour = rs.getTime(column).toLocalTime().getHour();
        int min = rs.getTime(column).toLocalTime().getMinute();
        
        LocalDate appointmentDate = LocalDate.of(year, month, day);
        LocalTime appointmentTime = LocalTime.of(hour,min);

        ZonedDateTime userZDT = ZonedDateTime.of(appointmentDate, appointmentTime, ZoneId.of("Etc/GMT0")); //making user appointment to ZDT
        Instant userToGMT = userZDT.toInstant(); //user to GMT(UTC)
        
        ZonedDateTime gmtToLocal = userToGMT.atZone(ZoneId.systemDefault());
        
        return gmtToLocal.toLocalDateTime();
    }
    
    public static String concatenateDate(LocalDate date, LocalTime hour){ //to put into a query
        ZonedDateTime userZDT = ZonedDateTime.of(date, hour, ZoneId.systemDefault());
        Instant userToGMT = userZDT.toInstant(); //user to GMT(UTC)
        ZonedDateTime gmtToLocal = userToGMT.atZone(ZoneId.of("Etc/UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = gmtToLocal.format(formatter);
        String dateConcat = formattedDate+" "+gmtToLocal.getHour()+":"+gmtToLocal.getMinute()+":"+gmtToLocal.getSecond();
        return dateConcat;
    }
    
    //methods for Time and field check (overload)
    public static Boolean checkOverlappingAppointment(LocalDate userDate, LocalTime userStart, LocalTime userEnd, Appointment appointment) throws Exception{
        Boolean noOverlap = false;
        String userDateStart = concatenateDate(userDate, userStart);
        String userDateEnd = concatenateDate(userDate, userEnd);
        String myWhereQuery = "( appointmentId !="+appointment.getAppointmentId().toString()+")"
                + "AND ((start BETWEEN \'"+userDateStart+"\' AND \'"+userDateEnd+"\')"
                + " OR (end BETWEEN \'"+userDateStart+"\' AND \'"+userDateEnd+"\')"
                + " OR (start < \'"+userDateStart+"\' AND end > \'"+userDateEnd+"\'))";
        ObservableList filteredAppointments = getFilteredAppointment(myWhereQuery);
        if(filteredAppointments.size()>0){
            noOverlap = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("You appointment is overlapping with other, please select another time");
            alert.showAndWait();  
        }
        else{
            noOverlap = true;
        }
        
        return noOverlap;
    }
    
    public static Boolean checkOverlappingAppointment(LocalDate userDate, LocalTime userStart, LocalTime userEnd) throws Exception{
        Boolean noOverlap = false;
        String userDateStart = concatenateDate(userDate, userStart);
        String userDateEnd = concatenateDate(userDate, userEnd);
        String myWhereQuery ="(start BETWEEN \'"+userDateStart+"\' AND \'"+userDateEnd+"\')"
                + " OR (end BETWEEN \'"+userDateStart+"\' AND \'"+userDateEnd+"\')"
                + " OR (start < \'"+userDateStart+"\' AND end > \'"+userDateEnd+"\')";
        ObservableList filteredAppointments = getFilteredAppointment(myWhereQuery);
        if(filteredAppointments.size()>0){
            noOverlap = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("You appointment is overlapping with other, please select another time");
            alert.showAndWait();  
        }
        else{
            noOverlap = true;
        }
        return noOverlap;
    }
    
    public static Boolean checkFieldsAppointment(int customerId, int type, String date, LocalTime start, LocalTime end) throws Exception{
        Boolean validAll = true;
        Boolean validCustomerId = true;
        Boolean validType = true;
        Boolean validStart = true;
        String errorMessage = "";
        if(customerId == -1){
            validCustomerId = false;
            errorMessage = "customer";
        }
        if(type == -1){
            validType = false;
            errorMessage = "type";
        }
        if(date.equals("")){
            validStart = false;
            errorMessage = "day selection"; 
        }
        if(start.getHour() == 0 || end.getHour() == 0){
            validStart = false;
            errorMessage = "hour selection";
        }
        if (start.isAfter(end)){
            validStart = false;
            errorMessage = "Date, can't register end of appointment before start";
        }
        
        if(validCustomerId && validType && validStart){
            validAll = true;
        }
        else{
            validAll = false;
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Error!");
            alert3.setContentText("Invalid "+errorMessage);
            alert3.showAndWait();  
        }
        return validAll;
    }
    
    //method to check if next app is in <15min
    public static ObservableList<Appointment> checkNextAppointment(LocalDate date, LocalTime time) throws Exception{
        String userTime = DataProvider.concatenateDate(date, time);
        String condition = "start between \'"+userTime+"\' and date_add(\'"+userTime+"\', interval+15 minute)";
        String myQuery = "SELECT * FROM appointment WHERE "+condition;        
        allAppointment = Query.executeQueryToList(myQuery, rs -> new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"), 
                    rs.getInt("userId"), 
                    rs.getString("title"), 
                    rs.getString("description"), 
                    rs.getString("location"), 
                    rs.getString("contact"), 
                    rs.getString("type"), 
                    rs.getString("url"), 
                    timeZoneConverter(rs, "start"),
                    timeZoneConverter(rs, "end"),
                    rs.getDate("createDate"), 
                    rs.getString("createdBy"), 
                    rs.getDate("lastUpdate"), 
                    rs.getString("lastUpdateBy")));
        return allAppointment;
    }
    
    //methods to Get different fields from DB
    public static ObservableList<Customer> getAllCustomer() throws Exception{
        System.out.println("Getting all Customers");
        String myQuery = "SELECT * FROM customer";
        //LAMBDA EXPRESSION: used to get a list of results of the required type as soon as the query is executed. With this we avoid writing the code multiple types everything stays in the bussines logic
        allCustomer = Query.executeQueryToList(myQuery, rs -> new Customer(
                    rs.getInt("customerId"), 
                    rs.getString("customerName"), 
                    rs.getInt("addressId"), 
                    rs.getBoolean("active"), 
                    rs.getDate("createDate"), 
                    rs.getString("createdBy"), 
                    rs.getDate("lastUpdate"), 
                    rs.getString("lastUpdateBy")));
        return allCustomer;
    }
    
    public static ObservableList<Appointment> getAllAppointment() throws Exception{
        System.out.println("Getting all appointments");
        String conditionUserId = "userId = "+DataProvider.getId.getIdMethod("userId","user","userName = \'"+getUsername()+"\'");
        String myQuery = "SELECT * FROM appointment WHERE "+conditionUserId;
        allAppointment = Query.executeQueryToList(myQuery, rs -> new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"), 
                    rs.getInt("userId"), 
                    rs.getString("title"), 
                    rs.getString("description"), 
                    rs.getString("location"), 
                    rs.getString("contact"), 
                    rs.getString("type"), 
                    rs.getString("url"), 
                    timeZoneConverter(rs, "start"),
                    timeZoneConverter(rs, "end"),
                    rs.getDate("createDate"), 
                    rs.getString("createdBy"), 
                    rs.getDate("lastUpdate"), 
                    rs.getString("lastUpdateBy")));
        return allAppointment;
    }
    
    public static ObservableList<Appointment> getFilteredAppointment(String filter) throws Exception{
        System.out.println("Getting filtered appointments");
        String condition;
        String conditionUserId = "userId = "+DataProvider.getId.getIdMethod("userId","user","userName = \'"+getUsername()+"\'");
        if(filter.equals("month")){
            condition = " WHERE start between current_date() and date_add(current_date(), interval+1 month) AND "+conditionUserId;            
        }
        else if (filter.equals("week")){
          condition = " WHERE start between current_date() and (DATE_ADD((SELECT CURDATE() - INTERVAL (WEEKDAY(CURDATE())+1)DAY),INTERVAL 2 WEEK)) AND "+conditionUserId;  
        }
        else{
            condition = " WHERE "+filter+" AND "+conditionUserId;
        }
        String myQuery = "SELECT * FROM appointment"+condition;        
        allAppointment = Query.executeQueryToList(myQuery, rs -> new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"), 
                    rs.getInt("userId"), 
                    rs.getString("title"), 
                    rs.getString("description"), 
                    rs.getString("location"), 
                    rs.getString("contact"), 
                    rs.getString("type"), 
                    rs.getString("url"), 
                    timeZoneConverter(rs, "start"),
                    timeZoneConverter(rs, "end"),
                    rs.getDate("createDate"), 
                    rs.getString("createdBy"), 
                    rs.getDate("lastUpdate"), 
                    rs.getString("lastUpdateBy")));
        return allAppointment;
    }
    
    public static ObservableList<Appointment> filterUserAppBySelection(String filter) throws Exception{
        String myQuery = "SELECT * FROM appointment WHERE "+filter;        
        allAppointment = Query.executeQueryToList(myQuery, rs -> new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"), 
                    rs.getInt("userId"), 
                    rs.getString("title"), 
                    rs.getString("description"), 
                    rs.getString("location"), 
                    rs.getString("contact"), 
                    rs.getString("type"), 
                    rs.getString("url"), 
                    timeZoneConverter(rs, "start"),
                    timeZoneConverter(rs, "end"),
                    rs.getDate("createDate"), 
                    rs.getString("createdBy"), 
                    rs.getDate("lastUpdate"), 
                    rs.getString("lastUpdateBy")));
        return allAppointment;
    }
    
    public static ObservableList<String> getAllElements(String select, String from, String condition) throws Exception{
        String myQuery;
        ObservableList<String> list;
        if (condition.equals("")){
            myQuery = "SELECT "+select+" FROM "+from;
        }
        else{
            myQuery = "SELECT "+select+" FROM "+from+" WHERE "+condition;
        }
        list = Query.executeQueryToList(myQuery, rs -> rs.getString(select));
        return list;
    }
    
    public static ObservableList<String> getAllDistinctElements(String select, String from, String condition) throws Exception{
        ObservableList<String> list;
        String myQuery;
        if (condition.equals("")){
            myQuery = "SELECT DISTINCT "+select+" FROM "+from;
        }
        else{
            myQuery = "SELECT "+select+" FROM "+from+" WHERE "+condition;
        }
        list = Query.executeQueryToList(myQuery, rs -> rs.getString(select));
        return list;
    }
    
    public static String getTextFromDB(String whatSelect, String fromTable1, String condition) throws Exception{
        ObservableList<String> list;
        String myQuery = "SELECT "+whatSelect+" FROM "+fromTable1+" WHERE "+condition;
        list = Query.executeQueryToList(myQuery, rs -> rs.getString(whatSelect));
        return list.get(0);
    }

    public static ObservableList<AppByType> getAppByType() throws Exception{
        String conditionUserId = "userId = "+DataProvider.getId.getIdMethod("userId","user","userName = \'"+getUsername()+"\'");
        String myQuery = "SELECT year(start), monthname(start), " +
            "    sum(case when type = 'Scrum' then 1 else 0 end) AS scrum, " +
            "    sum(case when type = 'Presentation' then 1 else 0 end) AS presentation, " +
            "    sum(case when type = 'Other' then 1 else 0 end) AS other " +
            "FROM appointment WHERE " +conditionUserId+
            " GROUP BY monthname(start)";       
        allAppByType = Query.executeQueryToList(myQuery, rs -> new AppByType (
                    rs.getInt("year(start)"),
                    rs.getString("monthname(start)"),
                    rs.getInt("scrum"),
                    rs.getInt("presentation"),
                    rs.getInt("other")));
        return allAppByType;
    }
    
    //methods for DB manipulation
    public static void updateField (String tableToUpdate, String columnToUpdate, String valueToSet, String condition) throws Exception{
        Query.makeQuery("UPDATE " + tableToUpdate + 
                " SET " + columnToUpdate + " = " + "\'" + valueToSet + "\'"
                + ", lastUpdateBy = \'" + DataProvider.getUsername() + "\'"
                +" WHERE "+ condition
        );
    }
    
    public static void createField (String tableToInsert, String columns, String values) throws Exception{
        String q = "INSERT INTO " + tableToInsert + " (" + columns + ", createDate, createdBy, lastUpdateBy" + ") "
                + "VALUES (" + values + ", current_date(), \'"+DataProvider.getUsername()+"\', \'"+DataProvider.getUsername() + "\')";
        Query.makeQuery(q);
    }
    
    //methods for DB log file manipulation
    public static void manipulateFile(String writing) throws IOException {  
        //Create the file
        if (file.createNewFile()){
          System.out.println("File is created!");
        }else{
          System.out.println("File already exists.");
        }

        //Write Content
        String writingAll = ZonedDateTime.now().toString()+" "+writing;
        PrintWriter write = new PrintWriter(new FileWriter(file, true));
        write.println(writingAll);
        write.close();

   
    }
    
    public static void openFile() throws IOException { 
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        } else {
            System.out.println("Unable to open DatabaseLog file from program, please find it on: \'c://temp//logFile.txt\'");
        }
    }
    
    //Added after 1st attempt, method to check valid customer information
    public static Boolean checkFieldsCustomer(String customerName, String address, String country, String city, String postCode, String phone) throws Exception{
        Boolean validAll = true;
        Boolean validCustomerName = true;
        Boolean validAddress = true;
        Boolean validCountry = true;
        Boolean validCity = true;
        Boolean validPostcode = true;
        Boolean validPhone = true;
        String errorMessage = "";
        if(customerName.equals("")){
            validCustomerName = false;
            errorMessage = "customer name, please enter a customer name";
        }
        if(address.equals("")){
            validAddress = false;
            errorMessage = "address, please enter at least one valid address";
        }
        if(country.equals("")){
            validCountry = false;
            errorMessage = "country, please select a country"; 
        }
        if(city.equals("")){
            validCity = false;
            errorMessage = "city, please select a city"; 
        }
        if(postCode.equals("")){
            validPostcode = false;
            errorMessage = "postal code, please enter a postal code"; 
        }
        if(phone.equals("")){
            validPhone = false;
            errorMessage = "phone, please enter a phone"; 
        }       
        if(validCustomerName && validAddress && validCountry && validCity && validPostcode && validPhone){
            validAll = true;
        }
        else{
            validAll = false;
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Error!");
            alert3.setContentText("Invalid "+errorMessage);
            alert3.showAndWait();  
        }
        return validAll;
    }
    
}
