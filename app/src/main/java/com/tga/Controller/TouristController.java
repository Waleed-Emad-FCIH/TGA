package com.tga.Controller;

import android.icu.text.DateFormat;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.PlaceModel;
import com.tga.models.PlanModel;
import com.tga.models.PostModel;
import com.tga.models.TouristModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 3/9/18.
 */

public class TouristController extends UserController implements DB_Interface {

    private TouristModel touristModel;

    public TouristController(String id, String email, String pass, String name,
                             String phoneNo, String adrs, String photo, String nationality,
                             ArrayList<String> historyPlans, ArrayList<String> historyPrograms) {
        super(id, email, pass, name, phoneNo, adrs);
        this.touristModel = new TouristModel();
        touristModel.photo = photo;
        touristModel.email = email;
        touristModel.password = pass;
        touristModel.name = name;
        touristModel.phoneNumber = phoneNo;
        touristModel.address = adrs;
        touristModel.nationality = nationality;
        touristModel.myPlansID = historyPlans;
        touristModel.myProgramsID = historyPrograms;
        touristModel.id = id;

    }

    public void saveToDB() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        touristModel.id = dRef.push().getKey();
        dRef.child(touristModel.id).setValue(touristModel);
    }

    @Override
    public void delFromDB() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).removeValue();
    }

    @Override
    public void updateToDB() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        touristModel.id = dRef.push().getKey();
        dRef.child(touristModel.id).setValue(touristModel);
    }


    public ArrayList<TouristModel> listAll() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final DatabaseReference tRef = fd.getReference("tourists");
        final ArrayList<TouristModel> touristModels = new ArrayList<>();

        tRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TouristModel touristModel = snapshot.getValue(TouristModel.class);
                    touristModels.add(touristModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return touristModels;
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
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference(" tourists");
        dRef.child(getId()).setValue(touristModel);
    }

    public ArrayList<String> getHistoryPlans() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final  DatabaseReference tRef = fd.getReference("tourists").child(getId());

        final DatabaseReference pRef = fd.getReference("plan");
        final ArrayList<String> historyPlansID = new ArrayList<>();

        tRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              HashMap<String, Object> tm  = (HashMap<String, Object>) dataSnapshot.getValue();
             ArrayList<String>myPlansID = (ArrayList<String>) tm.get("myPlansID");
                for (String id : myPlansID)
               {
                   final String planID = id;
                   pRef.child(planID).child("endDate").addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                               if(dataSnapshot.getValue()!=null && isValidPlan(dataSnapshot.getValue().toString()))
                               {
                                   historyPlansID.add(planID);
                               }
                           }
                           catch (Exception e)
                           {

                            }
                        }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  historyPlansID;
    }

    public ArrayList<String> getHistoryPrograms() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final  DatabaseReference tRef = fd.getReference("tourists").child(getId());
        final DatabaseReference pRef = fd.getReference("programs");
        final ArrayList<String> historyProgramsID = new ArrayList<>();

        tRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> tm  = (HashMap<String, Object>) dataSnapshot.getValue();
                ArrayList<String>myPlansID = (ArrayList<String>) tm.get("myProgramsID");
                for (String id : myPlansID)
                {
                    final String planID = id;
                    pRef.child(planID).child("endDate").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                if(dataSnapshot.getValue()!=null && isValidPlan(dataSnapshot.getValue().toString()))
                                {
                                    historyProgramsID.add(planID);
                                }
                            }
                            catch (Exception e)
                            {

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  historyProgramsID;
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
        dRef.child(getId()).setValue(touristModel);

    }
    public  String getId()
    {
      return  touristModel.id;
    }

    public void addProgram(String programID)
    {
        touristModel.myProgramsID.add(programID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).setValue(touristModel);

    }

    public void delPlan(String planID) {
        touristModel.myPlansID.remove(planID);
        FirebaseDatabase fd  = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).setValue(touristModel);
    }

    public void delProgram(String programID) {

        touristModel.myProgramsID.remove(programID);
        FirebaseDatabase fd  = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).setValue(touristModel);
    }


    public void editMyPlan(String planID) {

    }

    public void editMyProgram(String programID) {

    }

    public void favouritePlace(String placeID){

        touristModel.myFavouritePlacesID.add(placeID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).setValue(touristModel);
    }

    public void favouriteProgram(String programID){
        touristModel.myFavouriteProgramsID.add(programID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).setValue(touristModel);
    }

    public void unFavouritePlace(String placeID){
        touristModel.myFavouritePlacesID.remove(placeID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).setValue(touristModel);
    }

    public void unFavouriteProgram(String programID){
        touristModel.myFavouriteProgramsID.remove(programID);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        dRef.child(getId()).setValue(touristModel);
    }

    @Override
    public void login(String email, String pass) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void editProfile() {

    }

    @Override
    public void search() {

    }
    public boolean isValidPlan(String date)
    {
        int Year = Integer.parseInt(date.substring(0,4));
        int Month = Integer.parseInt(date.substring(5,7));
        int Day = Integer.parseInt(date.substring(8,10 ));

        Date currentDate = new Date();
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = dateFormat.format(currentDate).toString();

        int currentYear = Integer.parseInt(strDate.substring(0,4));
        int currentMonth = Integer.parseInt(strDate.substring(5,7));
        int currentDay = Integer.parseInt(strDate.substring(8,10 ));

        if(currentYear>Year)
        {
            return false;
        }
        else if(currentMonth>Month &&currentYear>Year)
        {
            return  false;
        }
        else if(currentDay>Day&&currentMonth>Month &&currentYear>Year)
        {
            return false;
        }
        else
            return true;

    }

}
