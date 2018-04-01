package com.tga.Controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.PlanModel;
import com.tga.models.PostModel;
import com.tga.models.TouristModel;

import java.util.ArrayList;
import java.util.Iterator;

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
    public  void create(){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef =fd.getReference("tourists");
        touristModel.id = dRef.getKey();
        dRef.child(getId()).setValue(touristModel);
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

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference(" tourists");
        dRef.child(getId()).child("nationality").setValue(nationality);
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
        FirebaseDatabase fd  = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("plansID").setValue(touristModel.myPlansID);

    }

    public void addProgram(String programID)
    {
        touristModel.myProgramsID.add(programID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("programsID").setValue(touristModel.myProgramsID);

    }

    public void delPlan(String planID) {
        touristModel.myPlansID.remove(planID);
        FirebaseDatabase fd  = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("plansID").setValue(touristModel.myPlansID);
    }

    public void delProgram(String programID) {

        touristModel.myProgramsID.remove(programID);
        FirebaseDatabase fd  = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("programsID").setValue(touristModel.myProgramsID);
    }


    public void editMyPlan(String planID) {

    }

    public void editMyProgram(String programID) {

    }

    public void favouritePlace(String placeID){

        touristModel.myFavouritePlacesID.add(placeID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("FavouritePlacesID").setValue(touristModel.myFavouritePlacesID);
    }

    public void favouriteProgram(String programID){
        touristModel.myFavouriteProgramsID.add(programID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("FavouriteProgramsID").setValue(touristModel.myFavouriteProgramsID);
    }

    public void unFavouritePlace(String placeID){
        touristModel.myFavouritePlacesID.remove(placeID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("FavouritePlacesID").setValue(touristModel.myFavouritePlacesID);
    }

    public void unFavouriteProgram(String programID){
        touristModel.myFavouriteProgramsID.remove(programID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).child("FavouriteProgramsID").setValue(touristModel.myFavouriteProgramsID);
    }
}
