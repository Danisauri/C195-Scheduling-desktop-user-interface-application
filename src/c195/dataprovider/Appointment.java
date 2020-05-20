/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.dataprovider;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author danie
 */
public class Appointment extends TimeController {
    private Integer appointmentId;
    private Integer customerId;
    private Integer userId;
    private String customerName;
    private String userName;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    
    public Appointment (int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, LocalDateTime start, LocalDateTime end, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy){
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = getCreateDate();
        this.createdBy = getCreatedBy();
        this.lastUpdate = getLastUpdate();
        this.lastUpdateBy = getLastUpdateBy();
    }
    
    public Integer getAppointmentId(){
        return appointmentId;
    }
    public Integer getCustomerId(){
        return customerId;
    }
    public Integer getUserId(){
        return userId;
    }
    public String getCustomerName() throws Exception{
        customerName = DataProvider.getTextFromDB("customerName", "customer", "customerId = "+customerId);
        return customerName;
    }
    public String getUserName() throws Exception{
        userName = DataProvider.getTextFromDB("userName", "user", "userId = "+userId);
        return userName;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLocation(){
        return location;
    }
    public String getContact(){
        return contact;
    }
    public String getType(){
        return type;
    }
    public String getUrl(){
        return url;
    }
    public LocalDateTime getStart(){
        return start;
    }
    public LocalDateTime getEnd(){
        return end;
    }
    public String getStartDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm");
        String startDate = start.format(formatter);
        return startDate;  
    }
    public String getEndDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm");
        String endDate = end.format(formatter);
        return endDate;  
    }
    public String getStartHour(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedHour = start.format(formatter);
        return formattedHour;  
    }
    public String getEndHour(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedHour = end.format(formatter);
        return formattedHour;  
    }
    
    public void setAppointmentId(){
        this.appointmentId = appointmentId;
    }
    public void setCustomerId(){
        this.customerId = customerId;
    }
    public void setUserId(){
        this.userId = userId;
    }
    public void setTitle(){
        this.title = title;
    }
    public void setDescription(){
        this.description = description;
    }
    public void setLocation(){
        this.location = location;
    }
    public void setContact(){
        this.contact = contact;
    }
    public void setType(){
        this.type = type;
    }
    public void setUrl(){
        this.url = url;
    }
    public void setStart(){
        this.start = start;
    }
    public void setEnd(){
        this.end = end;
    }
    
    
}
