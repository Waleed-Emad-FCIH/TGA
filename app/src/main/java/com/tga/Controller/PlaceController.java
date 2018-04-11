package com.tga.Controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.model.Plan;
import com.tga.models.PlaceModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PlaceController  {
    private  PlaceModel placeModel;
    private  static FirebaseDatabase mRef = FirebaseDatabase.getInstance();
    static DatabaseReference  reference =mRef.getReference().child("places");

    public PlaceController(String id,String title, ArrayList<String> photos, String openTime, String closeTime,
                      String location, double rate, ArrayList<String> reviews){
        this.placeModel = new PlaceModel();
        placeModel.id = id;
        placeModel.photos = photos;
        placeModel.openTime = openTime;
        placeModel.closeTime = closeTime;
        placeModel.location = location;
        placeModel.rate = rate;
        placeModel.reviews = reviews;
    }

    public String getId() {
        return placeModel.id;
    }

    public ArrayList<String> getPhotos() {
        return placeModel.photos;
    }

    public void addPhoto(String photo) {
        placeModel.photos.add(photo);
    }

    public String getOpenTime() {
        return placeModel.openTime;
    }

    public void setOpenTime(String openTime) {
        placeModel.openTime = openTime;
    }

    public String getCloseTime() {
        return placeModel.closeTime;
    }

    public void setCloseTime(String closeTime) {
        placeModel.closeTime = closeTime;
    }

    public String getLocation() {
        return placeModel.location;
    }

    public void setLocation(String location) {
        placeModel.location = location;
    }

    public void delPlace(){ }

    public void editPlace() { }

    public void ratePlace(){ }
    public com.tga.model.PlaceModel getPlace(String title){
        DatabaseReference users = mRef.getReference("places");

        return null;
    }

    public double getRate(){

        return placeModel.rate;
    }

    public void addReview(String review){
        placeModel.reviews.add(review);
    }

    public ArrayList<String> getReviews(){
        return placeModel.reviews;
    }

    //======================== " imbo code " ======================
    private void saveToDB(PlaceModel Place) {
            reference.child(this.placeModel.id).setValue(Place);
        }
    private static ArrayList<Object> listAll() {
            final ArrayList<PlaceModel> Places= new ArrayList<>();
            mRef.getReference().child("plans").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        PlaceModel place = snapshot.getValue(PlaceModel.class);
                        Places.add(place);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            ArrayList<Object>OPlaces= new ArrayList<>();
            for(int i=0;i<Places.size();i++){
                Object s =(Object)Places.get(i);
                OPlaces.add(s);
            }
            return OPlaces ;
    }
    static  PlaceModel St= new PlaceModel();
    public static Object getByID(String id) {
        Query query = reference.orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 St= dataSnapshot.getValue(com.tga.models.PlaceModel.class);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  (Object)St;
    }
    private ArrayList<PlaceModel> delFromDB(String desiredPlace) {
        final ArrayList<PlaceModel> Desired= new ArrayList<>();

        DatabaseReference reference =mRef.getReference().child("places");
        Query query = reference.orderByChild("title").equalTo(desiredPlace);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
        return  Desired;
    }
    public void updateToDB() {

        Query query = reference.orderByChild("id").equalTo(this.placeModel.id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PlaceController.this.placeModel = dataSnapshot.getValue(PlaceModel.class);
                reference.child(PlaceController.this.placeModel.id).setValue(PlaceController.this.placeModel);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //==================================================================
}
