package com.tga.Controller;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.ProgramModel;
import com.tga.models.TouristModel;
import com.tga.models.TouristPlan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

/**
 * Created by root on 3/9/18.
 */

public class TouristController extends UserController implements DB_Interface {

    private TouristModel touristModel;
    private DatabaseReference dRef;

    public TouristController(String id, String email, String pass, String name,
                             String phoneNo, String adrs, String photo, String nationality,
                             ArrayList<String> myPlans, ArrayList<String> myPrograms) {
        super(id, email, pass, name, phoneNo, adrs);
        this.touristModel = new TouristModel();
        touristModel.photo = photo;
        touristModel.email = email;
        touristModel.password = pass;
        touristModel.name = name;
        touristModel.phoneNumber = phoneNo;
        touristModel.address = adrs;
        touristModel.nationality = nationality;
        touristModel.myPlansID = myPlans;
        touristModel.myProgramsID = myPrograms;
        touristModel.id = id;
        touristModel.myFavouritePlacesID = new ArrayList<>();
        touristModel.myFavouriteProgramsID = new ArrayList<>();
    }



    private TouristController(TouristModel tm){
        super(tm.id, tm.email, tm.password, tm.name, tm.phoneNumber, tm.address);
        this.touristModel = tm;
        touristModel.id = tm.id;
        touristModel.email = tm.email;
        touristModel.password = tm.password;
        touristModel.name = tm.name;
        touristModel.phoneNumber = tm.phoneNumber;
        touristModel.address = tm.address;
    }

    public TouristController(String id, String email, String pass, String name, String phoneNo, String adrs) {
        super(id, email, pass, name, phoneNo, adrs);
    }

    public void saveToDB() {
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("tourists");
        dRef.child(getId()).setValue(touristModel);
    }

    @Override
    public void delFromDB() {
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("tourists");
        dRef.child(getId()).removeValue();
    }

    @Override
    public void updateToDB() {
        saveToDB();
    }


    public static void listAll(SimpleCallback<ArrayList<TouristController>> simpleCallback) {
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("tourists");
        final ArrayList<TouristController> touristModels = new ArrayList<>();

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TouristModel touristModel = snapshot.getValue(TouristModel.class);
                    touristModels.add(new TouristController(touristModel));
                }
                simpleCallback.callback(touristModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                simpleCallback.callback(touristModels);
            }
        });
    }

    /*public static TouristController getByID(String id) {
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();


        final TouristModel[] tm = new TouristModel[1];
        final Semaphore semaphore = new Semaphore(0);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    System.out.println("onDataChange and data exists = " + data.exists());
                    tm[0] = data.getValue(TouristModel.class);
                }
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TouristController", "Failed to read value by ID.", databaseError.toException());
                semaphore.release();
            }
        });
        System.out.println("Semaphore locked");
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Semaphore opened");
        if (tm[0] == null)
            return null;
        else
            return new TouristController(tm[0]);
    }*/


    public static void getByID(@NonNull final SimpleCallback<TouristController> finishedCallback, String id) {
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        Query q = dRef.child("tourists").orderByChild("id").equalTo(id);
        final TouristModel[] tm = new TouristModel[1];
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    tm[0] = data.getValue(TouristModel.class);
                    if (tm[0] == null)
                        finishedCallback.callback(null);
                    else
                        finishedCallback.callback(new TouristController(tm[0]));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TouristController", "Failed to read value by ID.", databaseError.toException());
            }
        });
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



    public void getHistoryPlans(SimpleCallback<ArrayList> plans) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final DatabaseReference tpRef = fd.getReference("touristsPlans");
        Query q = tpRef.orderByChild("userId").equalTo(this.getId());
        final ArrayList<String> historyPlansID = new ArrayList<>();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    TouristPlan touristPlan = dataSnapshot1.getValue(TouristPlan.class);
                    if(!isValidPlan(touristPlan.planDate))
                    {
                        historyPlansID.add(touristPlan.planID);
                    }
                }
               plans.callback(historyPlansID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public  void getHistoryPrograms(SimpleCallback<ArrayList> programs) {
        ArrayList<String> historyProgramsID = new ArrayList<>();
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        final  DatabaseReference tRef = fd.getReference("tourists").child(getId());
        final DatabaseReference pRef = fd.getReference("programs");
        tRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> tm  = (HashMap<String, Object>) dataSnapshot.getValue();
                ArrayList<String>myPlansID = (ArrayList<String>) tm.get("myProgramsID");
                try {
                    for (String id : myPlansID) {
                        final String planID = id;
                        pRef.child(planID).child("endDate").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    if (dataSnapshot.getValue() != null && isValidPlan(dataSnapshot.getValue().toString())) {
                                        historyProgramsID.add(planID);
                                    }
                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
                catch (Exception e)
                {

                }
                programs.callback(historyProgramsID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<String> getMyPlans() {
        if (touristModel.myPlansID == null)
            touristModel.myPlansID = new ArrayList<>();
        return touristModel.myPlansID;
    }

    public ArrayList<String> getMyPrograms() {
        if (touristModel.myProgramsID == null)
            touristModel.myProgramsID = new ArrayList<>();
        return touristModel.myProgramsID;
    }


    public void addPlan(String planID , String userId , String planTime) {
        final boolean[] flag = {false};
        FirebaseDatabase fd  = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
        DatabaseReference tpRef = fd.getReference("touristsPlans");
        dRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TouristModel touristModel = dataSnapshot.getValue(TouristModel.class);
                try {
                    if(!flag[0])
                    {
                        touristModel.myPlansID.add(planID);

                        TouristPlan touristPlan = new TouristPlan(planID,false , planTime  , userId);
                        touristPlan.id = tpRef.push().getKey();
                        tpRef.child(touristPlan.id).setValue(touristPlan);
                        flag[0] = true;
                    }

                }
                catch (Exception e)
                {
                    touristModel.myPlansID = new ArrayList<>();
                    touristModel.myPlansID.add(planID);
                    TouristPlan touristPlan = new TouristPlan(planTime,false , planID  , userId);
                    touristPlan.id = tpRef.push().getKey();
                    tpRef.child(touristPlan.id).setValue(touristPlan);
                    flag[0] =true;
                }


                    dRef.child(userId).setValue(touristModel);
                    flag[0] = true;




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public  String getId()
    {
        return  touristModel.id;
    }

    public void addProgram(String programID)
    {
        if (touristModel.myProgramsID == null)
            touristModel.myProgramsID = new ArrayList<>();
        touristModel.myProgramsID.add(programID);
        updateToDB();
    }

    public void delPlan(String planID , String userId) {
        final boolean[] flag = {false};
        FirebaseDatabase fd  = FirebaseDatabase.getInstance();
        DatabaseReference dRef = fd.getReference("tourists");
       //    DatabaseReference tpRef = fd.getReference("touristsPlans");
        dRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TouristModel touristModel = dataSnapshot.getValue(TouristModel.class);
                try {
                    if(!flag[0])
                    {
                        touristModel.myPlansID.remove(planID);
                        flag[0] = true;
                    }

                }
                catch (Exception e)
                {

                }
                dRef.child(userId).setValue(touristModel);
                flag[0] = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void delProgram(String programID) {
        if (touristModel.myProgramsID == null)
            touristModel.myProgramsID = new ArrayList<>();
        touristModel.myProgramsID.remove(programID);
        updateToDB();
    }


    public void editMyPlan(String planID) {

    }

    public void editMyProgram(String programID, final String title, final String desc,
                              final String startDate, final String endDate, final String hotelName) {
        ProgramController.getByID(new SimpleCallback<ProgramController>() {
            @Override
            public void callback(ProgramController pc) {
                pc.editProgram(title, desc, startDate, endDate, hotelName);

            }
        }, programID);
    }

    public void favouritePlace(String placeID){

        touristModel.myFavouritePlacesID.add(placeID);
        updateToDB();
    }

    public void favouriteProgram(String programID){
        if (touristModel.myFavouriteProgramsID == null)
            touristModel.myFavouriteProgramsID = new ArrayList<>();
        touristModel.myFavouriteProgramsID.add(programID);
        updateToDB();
    }

    public void unFavouritePlace(String placeID){
        touristModel.myFavouritePlacesID.remove(placeID);
        updateToDB();
    }

    public void unFavouriteProgram(String programID){
        touristModel.myFavouriteProgramsID.remove(programID);
        updateToDB();
    }

    public ArrayList<String> getMyFavouritPrograms(){
        if (this.touristModel.myFavouriteProgramsID == null)
            return new ArrayList<>();
        return this.touristModel.myFavouriteProgramsID;
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
        int Day = Integer.parseInt(date.substring(0,2));
        int Month = Integer.parseInt(date.substring(3,5));
        int Year = Integer.parseInt(date.substring(6,8));

        Date currentDate = new Date();
        java.text.DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String strDate = dateFormat.format(currentDate).toString();

        int currentDay = Integer.parseInt(strDate.substring(0,2));
        int currentMonth = Integer.parseInt(strDate.substring(3,5));
        int currentYear = Integer.parseInt(strDate.substring(6,8));

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
