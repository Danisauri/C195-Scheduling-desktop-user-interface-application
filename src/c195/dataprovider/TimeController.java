/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.dataprovider;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author danie
 */
public abstract class TimeController {
    public Date createDate;
    public String createdBy;
    public Date lastUpdate;
    public String lastUpdateBy;
    Timestamp timestamp;
    
    public Date getCreateDate(){
        return createDate;
    };
    public String getCreatedBy(){
        return createdBy;
    };
    public Date getLastUpdate(){
        return lastUpdate;
    };
    public String getLastUpdateBy(){
        return lastUpdateBy;
    };
    
    public void setCreateDate(){
        timestamp = new Timestamp(System.currentTimeMillis());
        this.createDate = timestamp;
    };
    public void setCreatedBy(){
        this.createdBy = createdBy;
    };
    public void setLastUpdateBy(){
        lastUpdateBy = DataProvider.getUsername();
        this.lastUpdateBy = lastUpdateBy;
    };
}
