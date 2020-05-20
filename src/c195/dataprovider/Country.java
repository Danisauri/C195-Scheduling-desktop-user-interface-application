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
public class Country extends TimeController{
    private Integer countryId;
    private String country;
    
    public Country (Integer countryId, String country,Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy){
        this.countryId = countryId;
        this.country = country;
        this.createDate = getCreateDate();
        this.createdBy = getCreatedBy();
        this.lastUpdate = getLastUpdate();
        this.lastUpdateBy = getLastUpdateBy();
    }
    
    public Integer getCountryId(){
        return countryId;
    }
    public String getCountry(){
        return country;
    }
    
    public void setCountryId(){
        this.countryId = countryId;
    }
    public void setCountry(){
        this.country = country;
    }
}
