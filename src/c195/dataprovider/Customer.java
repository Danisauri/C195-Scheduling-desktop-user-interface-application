/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.dataprovider;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author danie
 */
public class Customer extends TimeController{
    
    private int customerid;
    private String name;
    private Integer addressId;
    private Boolean active;

    
    public Customer (int customerid, String name, Integer addressId, Boolean active, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy){
        this.customerid = customerid;
        this.name = name;
        this.addressId = addressId;
        this.active = active;
        this.createDate = getCreateDate();
        this.createdBy = getCreatedBy();
        this.lastUpdate = getLastUpdate();
        this.lastUpdateBy = getLastUpdateBy();
    }
    
    public Integer getCustomerId(){
        return customerid;
    }
    public String getCustomerName(){
        return name;
    }
    public Integer getAddressId(){
        return addressId;
    }
    public Boolean getActive(){
        return active;
    }
    
    public void setCustomerName(){
        this.name = name;
    }
    public void setAddress(){
        this.addressId = addressId;
    }
    public void setActive(){
        this.active = active;
    }

    
}
