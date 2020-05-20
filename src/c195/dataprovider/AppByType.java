/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.dataprovider;

import c195.dataprovider.DataProvider;
import java.time.Month;

/**
 *
 * @author danie
 */
public class AppByType {
    private int year;
    private String month;
    private int scrum;
    private int presentation;
    private int other;
    
    public AppByType(int year, String month, int scrum, int presentation, int other){
        this.year = year;
        this.month = month;
        this.scrum = scrum;
        this.presentation = presentation;
        this.other = other;
    }
    
    public void setYear(){
        this.year = year;
    }
    public void setMonth(){
        this.month = month;
    }
    public void setScrum(){
        this.scrum = scrum;
    }
    public void setPresentation(){
        this.presentation = presentation;
    }
    public void setOther(){
        this.other = other;
    }
    public int getYear(){
        return year;
    }
    public String getMonth(){
        return month;
    }
    public int getScrum(){
        return scrum;
    }
    public int getPresentation(){
        return presentation;
    }
    public int getOther(){
        return other;
    }

}
