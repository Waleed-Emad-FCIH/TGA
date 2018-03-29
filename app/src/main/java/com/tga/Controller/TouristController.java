package com.tga.Controller;

import com.tga.models.TouristModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class TouristController extends UserController {
    
    private TouristModel touristModel;
    
    public TouristController(String id, String email, String pass, String name,
                             String phoneNo, String adrs, String photo, String nationality,
                             ArrayList<String> historyPlans, ArrayList<String> historyPrograms) {
        super(id, email, pass, name, phoneNo, adrs);
        this.touristModel = new TouristModel();
        touristModel.photo = photo;
        touristModel.nationality = nationality;
        touristModel.myPlansID = historyPlans;
        touristModel.myProgramsID = historyPrograms;
    }

    public String getPhoto() {
        return touristModel.photo;
    }

    public void setPhoto(String photo) {
        touristModel.photo = photo;
    }

    public String getNationality() {
        return touristModel.nationality;
    }

    public void setNationality(String nationality) {
        touristModel.nationality = nationality;
    }

    public ArrayList<String> getHistoryPlans() {
        return new ArrayList<String>(); //del this line
        // historyPlans is the myPlans but it's date expired
    }

    public ArrayList<String> getHistoryPrograms() {
        return new ArrayList<String>(); //del this line
        // historyPrograms is the myPrograms but it's date expired
    }

    public ArrayList<String> getMyPlans() {
        return touristModel.myPlansID;
    }

    public ArrayList<String> getMyPrograms() {
        return touristModel.myProgramsID;
    }

    public void addPlan(String planID) {
        touristModel.myPlansID.add(planID);
    }

    public void addProgram(String programID) {
        touristModel.myProgramsID.add(programID);
    }

    public void delPlan(String planID) {
        touristModel.myPlansID.remove(planID);
    }

    public void delProgram(String programID) {
        touristModel.myProgramsID.remove(programID);
    }

    public void editMyPlan(String planID) { }

    public void editMyProgram(String programID) { }

    public void favouritePlace(String placeID){
        touristModel.myFavouritePlacesID.add(placeID);
    }

    public void favouriteProgram(String programID){
        touristModel.myFavouriteProgramsID.add(programID);
    }

    public void unFavouritePlace(String placeID){
        touristModel.myFavouritePlacesID.remove(placeID);
    }

    public void unFavouriteProgram(String programID){
        touristModel.myFavouriteProgramsID.remove(programID);
    }
}
