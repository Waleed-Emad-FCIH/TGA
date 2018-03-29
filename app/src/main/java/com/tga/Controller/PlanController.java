package com.tga.Controller;

import com.tga.models.PlanModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PlanController {
    private PlanModel planModel;

    public PlanController(String id, ArrayList<String> placesID, String startDate, String endDate,
                     String location){
        this.planModel = new PlanModel();
        planModel.id = id;
        planModel.placesID = placesID;
        planModel.startDate = startDate;
        planModel.endDate = endDate;
        planModel.location = location;
    }

    public String getId() {
        return planModel.id;
    }

    public ArrayList<String> getPlaces() {
        return planModel.placesID;
    }

    public void addPlaceID(String placeID) {
        planModel.placesID.add(placeID);
    }

    public void delPlaceID(String placeID) {
        planModel.placesID.remove(placeID);
    }

    public String getStartDate() {
        return planModel.startDate;
    }

    public void setStartDate(String startDate) {
        planModel.startDate = startDate;
    }

    public String getEndDate() {
        return planModel.endDate;
    }

    public void setEndDate(String endDate) {
        planModel.endDate = endDate;
    }

    public String getLocation() {
        return planModel.location;
    }

    public void setLocation(String location) {
        planModel.location = location;
    }

    public void delPlan() { }

    public void editPlan(){ }

}
