package com.tga.models;


import java.util.ArrayList;

/**
 * Created by asus pc on 2/28/2018.
 */

public class AgentModel extends UserModel {

    public String photo;
    public String registrationNumber;
    public ArrayList<String> myProgramsID;
    public String name;
    public boolean State;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public ArrayList<String> getMyProgramsID() {
        return myProgramsID;
    }

    public void setMyProgramsID(ArrayList<String> myProgramsID) {
        this.myProgramsID = myProgramsID;
    }
}
