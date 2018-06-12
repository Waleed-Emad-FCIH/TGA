package com.tga.models;


import java.util.ArrayList;

/**
 * Created by asus pc on 2/28/2018.
 */

public class AgentModel extends UserModel {
public String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String photo;
    public String registrationNumber;
    public ArrayList<String> myProgramsID;
    public String name;
    String company;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
