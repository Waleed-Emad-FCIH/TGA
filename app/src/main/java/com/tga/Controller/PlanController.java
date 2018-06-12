package com.tga.Controller;

import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.PlanModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PlanController implements DB_Interface{
    private PlanModel planModel;
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();
    DatabaseReference reference =mRef.getReference().child("plans");
public  ArrayList<PlanModel> planModels =new ArrayList<>();
    public PlanController(String id, ArrayList<String> placesID, String startDate, String endDate,
                          String location, String description, String title ){
        this.planModel = new PlanModel();
        planModel.id = id;
        planModel.placesID = placesID;
        planModel.startDate = startDate;
        planModel.endDate = endDate;
        planModel.location = location;
        planModel.description = description;
        planModel.title = title ;
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



    //====================== " imbo code " ====================

/*
    private ArrayList<PlanModel> getSomePlan(String desiredPlan) {
        final ArrayList<PlanModel> Plans= new ArrayList<>();
        Query query = reference.orderByChild("location").equalTo(desiredPlan);
        query.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    PlanModel plan = snapshot.getValue(PlanModel.class);
                    Plans.add(plan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  Plans;
    }
*/

    @Override
    public void saveToDB() {
        planModel.creatorId  = FirebaseAuth.getInstance().getCurrentUser().getUid();
       planModel.setId(reference.push().getKey());
        reference.child(getId()).setValue(this.planModel);

    }

    @Override
    public void delFromDB() {
        Query query = reference.orderByChild("id").equalTo(this.planModel.id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            Boolean done = true;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for(DataSnapshot snapshot :dataSnapshot.getChildren())
                    {
                        // for deleting some place
                        snapshot.getRef().removeValue();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void updateToDB() {

       ;
        //Query query = reference.orderByChild("id").equalTo(this.planModel.id);
   reference.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   PlanController.this.planModel = dataSnapshot.getValue(PlanModel.class);
                reference.child(PlanController.this.planModel.id).setValue(PlanController.this.planModel);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public ArrayList<PlanModel> listAll (){
         ArrayList<PlanModel> Plans = new ArrayList();


            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<PlanModel> snP = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        PlanModel plan = snapshot.getValue(PlanModel.class);
                        Log.v("data??>>>", "here + " + plan.getTitle() + snP.size());
                        snP.add(plan);
                        Plans.add(plan);
                        planModels.add(plan);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        return Plans;
    }

    //=============================================================


}
