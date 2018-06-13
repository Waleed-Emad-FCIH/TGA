package com.tga.models;

/**
 * Created by asus pc on 6/13/2018.
 */

public class TouristPlan {
    public String planID;
    public boolean isNotified  = false;
    public String planDate;

    public TouristPlan() {
        this.planDate = planDate;
        this.planID = planID;
    }public TouristPlan(String planDate , String planID) {
        this.planDate = planDate;
        this.planID = planID;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }
}
