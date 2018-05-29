package com.tga.models;

import java.util.ArrayList;

/**
 * Created by asus pc on 2/28/2018.
 */

public class TouristModel extends UserModel {
    public String photo;
    public String nationality;
    public ArrayList<String> myPlansID;
    public ArrayList<String> myProgramsID;
    public ArrayList<String> myFavouritePlacesID;
    public ArrayList<String> myFavouriteProgramsID;

    public TouristModel() {

    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public ArrayList<String> getMyPlansID() {
        return myPlansID;
    }

    public void setMyPlansID(ArrayList<String> myPlansID) {
        this.myPlansID = myPlansID;
    }

    public ArrayList<String> getMyProgramsID() {
        return myProgramsID;
    }

    public void setMyProgramsID(ArrayList<String> myProgramsID) {
        this.myProgramsID = myProgramsID;
    }

    public ArrayList<String> getMyFavouritePlacesID() {
        return myFavouritePlacesID;
    }

    public void setMyFavouritePlacesID(ArrayList<String> myFavouritePlacesID) {
        this.myFavouritePlacesID = myFavouritePlacesID;
    }

    public ArrayList<String> getMyFavouriteProgramsID() {
        return myFavouriteProgramsID;
    }

    public void setMyFavouriteProgramsID(ArrayList<String> myFavouriteProgramsID) {
        this.myFavouriteProgramsID = myFavouriteProgramsID;
    }
}
