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
public class Address extends TimeController{
    private Integer addressId;
    private String address;
    private String address2;
    private Integer cityId;
    private String postalCode;
    private String phone;
    
    public Address (Integer addressId, String address, String address2, Integer cityId, String postalCode, String phone, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy){
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = getCreateDate();
        this.createdBy = getCreatedBy();
        this.lastUpdate = getLastUpdate();
        this.lastUpdateBy = getLastUpdateBy();
    }
    
    public Integer getAddressId(){
        return addressId;
    }
    public String getAddress(){
        return address;
    }
    public String getAddress2(){
        return address2;
    }
    public Integer getCityId(){
        return cityId;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public String getPhone(){
        return phone;
    } 
    
    
    public void setAddress(){
        this.address = address;
    }
    public void setAddress2(){
        this.address2 = address2;
    }
    public void setCityId(){
        this.cityId = cityId;
    }
    public void setPostalCode(){
        this.postalCode = postalCode;
    }
    public void setPhone(){
        this.phone = phone;
    }    
}
