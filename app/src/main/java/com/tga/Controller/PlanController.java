package com.tga.Controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.model.Plan;
import com.tga.model.User;
import com.tga.models.PlaceModel;
import com.tga.models.PlanModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PlanController {
    private PlanModel planModel;
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();

    public PlanController(String id, ArrayList<String> placesID, String startDate, String endDate,
                     String location){
        this.planModel = new PlanModel();
        planModel.id = id;
        planModel.placesID = placesID;
        planModel.startDate = startDate;
        planModel.endDate = endDate;
        planModel.location = location;
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

    public void delPlan() { }

    public void editPlan(){ }

    public void setPlan() {

    }
    //====================== " imbo code " ====================
    private void setPlan(Plan plan) {
        DatabaseReference plans = mRef.getReference("plans");
        plans.child(this.planModel.id).setValue(plan);
    }

    private ArrayList<PlanModel> getAllPlans() {
        final ArrayList<PlanModel> Plans= new ArrayList<>();
        mRef.getReference().child("plans").addListenerForSingleValueEvent(new ValueEventListener() {

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


    private ArrayList<PlanModel> getSomePlan(String desiredPlan) {
        final ArrayList<PlanModel> Plans= new ArrayList<>();
        mRef.getReference().child("plans");
        DatabaseReference reference =mRef.getReference().child("plans");
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

    private void delPlan(String desiredplan) {
        DatabaseReference reference =mRef.getReference().child("plans");
        Query query = reference.orderByChild("location").equalTo(desiredplan);
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

    //=============================================================

}
