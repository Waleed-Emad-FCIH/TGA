package com.tga.models;

/**
 * Created by asus pc on 6/13/2018.
 */

public class TouristPlan {
    public String planID;
    public boolean notified;
    public String planDate;
    public String id ;
    public String userId;

    public TouristPlan() {
    }


    public TouristPlan(String planID, boolean notified, String planDate ,  String userId) {
        this.planID = planID;
        this.notified = notified;
        this.planDate = planDate;
        this.userId = userId;
    }

    public TouristPlan(String planID, boolean notified, String planDate, String id, String userId) {
        this.planID = planID;
        this.notified = notified;
        this.planDate = planDate;
        this.id = id;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
