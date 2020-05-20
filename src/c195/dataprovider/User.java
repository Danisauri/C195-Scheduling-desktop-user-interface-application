/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.dataprovider;

import java.util.Date;

/**
 *
 * @author danie
 */
public class User extends TimeController{
    private Integer userId;
    private String userName;
    private String password;
    private Boolean active;
    
    public User (Integer userId, String userName, String password, Boolean active,Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createDate = getCreateDate();
        this.createdBy = getCreatedBy();
        this.lastUpdate = getLastUpdate();
        this.lastUpdateBy = getLastUpdateBy();
    }
    
    public Integer getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public Boolean getActive(){
        return active;
    }
    
    public void setUserId(){
        this.userId = userId;
    }
    public void setUserName(){
        this.userName = userName;
    }
    public void setPassword(){
        this.password = password;
    }
    public void setActive(){
        this.active = active;
    }
}
