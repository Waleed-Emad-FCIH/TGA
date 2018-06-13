package com.tga.models;

/**
 * Created by asus pc on 6/13/2018.
 */

public class TouristPlan {
    public String planID;
    public boolean notified;
    public String planDate;

    public TouristPlan() {
    }


    public TouristPlan(String planID, boolean notified, String planDate) {
        this.planID = planID;
        this.notified = notified;
        this.planDate = planDate;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }
}
