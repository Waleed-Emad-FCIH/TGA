package com.tga.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by asus pc on 2/28/2018.
 */

public class PlanModel {
    public String id ;
    public String title ;
    public  String shortInfo;
    public ArrayList<String> placesID ;
    public String startDate ;
    public String endDate ;
    public  String description;
    public String location ;
    public String  creatorId ;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getPlacesID() {
        return placesID;
    }

    public void setPlacesID(ArrayList<String> placesID) {
        this.placesID = placesID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
