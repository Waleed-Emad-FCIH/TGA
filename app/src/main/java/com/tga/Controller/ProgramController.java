package com.tga.Controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.DiscountModel;
import com.tga.models.ProgramModel;

import java.util.ArrayList;

public class ProgramController implements DB_Interface{

    private ProgramModel programModel;
    private Discount discount;
    private static DatabaseReference dbRef;

    private ProgramController(ProgramModel pm){
        this.programModel = pm;
        this.setDiscount(pm.discountID);
    }

    public ProgramController(String title, ArrayList<String> placesID, String description,
                        String startDate, String endDate, String hotelName, String ownerID){
        this.programModel = new ProgramModel();
        programModel.id = dbID("Programs");
        programModel.title = title;
        programModel.placesID = placesID;
        programModel.description = description;
        programModel.startDate = startDate;
        programModel.endDate = endDate;
        programModel.hotelName = hotelName;
        programModel.discountID = "";
        this.setDiscount("");
        programModel.rate = 0;
        programModel.hitRate = 0;
        programModel.reviews = new ArrayList<>();
        programModel.registeredTouristsID = new ArrayList<>();
        programModel.ownerID = ownerID;
        programModel.price = 0;
    }

    private String dbID(String ref){
        dbRef = FirebaseDatabase.getInstance().getReference(ref);
        String id = dbRef.push().getKey();
        return id;
    }

    public String getId() {
        return programModel.id;
    }

    public String getOwnerID(){
        return programModel.ownerID;
    }
    // TODO: add price edit text for agent view and xml
    public double getPrice(){
        return this.programModel.price;
    }

    public void setPrice(double p){
        this.programModel.price = p;
    }

    public String getTitle() {
        return programModel.title;
    }

    public void setTitle(String title) {
        programModel.title = title;
    }

    public ArrayList<String> getPlacesID() {
        return programModel.placesID;
    }

    public void addPlace(String placeID) {
        programModel.placesID.add(placeID);
    }

    public void delPlace(String placeID){
        if (!programModel.placesID.isEmpty())
            programModel.placesID.remove(placeID);
    }

    public String getDescription() {
        return programModel.description;
    }

    public void setDescription(String description) {
        programModel.description = description;
    }

    public String getStartDate() {
        return programModel.startDate;
    }

    public void setStartDate(String startDate) {
        programModel.startDate = startDate;
    }

    public String getEndDate() {
        return programModel.endDate;
    }

    public void setEndDate(String endDate) {
        programModel.endDate = endDate;
    }

    public String getHotelName() {
        return programModel.hotelName;
    }

    public void setHotelName(String hotelName) {
        programModel.hotelName = hotelName;
    }

    public double getRate(){
        if (programModel.hitRate == 0)
            return 0;
        return ((double) programModel.rate) / programModel.hitRate;
    }

    public void addReview(String review){
        programModel.reviews.add(review);
    }

    public ArrayList<String> getReviews() {
        return programModel.reviews;
    }

    public ArrayList<String> getRegisteredList(){
        return programModel.registeredTouristsID;
    }

    public void registeTourist(String touristID){
        programModel.registeredTouristsID.add(touristID);
    }

    public void unRegisteTourist(String touristID){
        programModel.registeredTouristsID.remove(touristID);
    }

    public void saveToDB(){
        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.child(this.getId()).setValue(this.programModel);
    }

    @Override
    public void updateToDB() {
        saveToDB();
    }

    public static void listAll(final SimpleCallback<ArrayList<ProgramController>> simpleCallback) {
        final ArrayList<ProgramController> list = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot progSnapshot : dataSnapshot.getChildren()){
                    ProgramModel model = progSnapshot.getValue(ProgramModel.class);
                    if (model != null)
                        list.add(new ProgramController(model));
                }
                simpleCallback.callback(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ProgramController", "Failed to read list values.", databaseError.toException());
                simpleCallback.callback(new ArrayList<ProgramController>());
            }
        });
    }

    public static void getByID(@NonNull final SimpleCallback<ProgramController> finishedCallback, String id) {
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query q = dbRef.child("Programs").orderByChild("id").equalTo(id);
        final ProgramModel[] pm = new ProgramModel[1];
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot progSnapshot : dataSnapshot.getChildren()) {
                    pm[0] = progSnapshot.getValue(ProgramModel.class);
                }
                if (pm[0] == null)
                    finishedCallback.callback(null);
                else
                    finishedCallback.callback(new ProgramController(pm[0]));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ProgramController", "Failed to read value by ID.", databaseError.toException());
            }
        });
    }

    public void delFromDB() {
        //data base delete prog and it's discount
        if (this.discount != null){
            delDiscount();
        }
        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.child(this.getId()).setValue(null);
    }

    public void editProgram(String title, String description, String startDate,
                            String endDate, String hotelName) {
        this.setTitle(title);
        this.setDescription(description);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setHotelName(hotelName);
    }

    public void rate(int n) {
        if (n > 5) { // for any error reason made rate > 5
            n %= 5;
            if (n == 0) // n = 10, 15, 20 or more
                n = 5;
        }
        this.programModel.hitRate++;
        this.programModel.rate += n;
    }

    public void setDiscount(String endDate, double discountPercentage){
        this.discount = new Discount(endDate, discountPercentage);
        this.programModel.discountID = this.discount.getId();
    }

    private void setDiscount(String discountID){
        if (discountID != null && !discountID.equals("")) {
            this.discount = new Discount("", 0);
            this.discount.getByID(new SimpleCallback<Discount>() {
                @Override
                public void callback(Discount data) {
                    discount = data;
                }
            }, discountID);
        } else
            this.discount = null;
    }

    public String getDiscountID() {
        return programModel.discountID;
    }

    public String getDiscountEndDate(){
        if (this.discount != null)
            return this.discount.getEndDate();
        else
            return "";
    }

    public double getDiscountPercentage(){
        if (this.discount != null)
            return this.discount.getDiscountPercentage();
        else
            return 0;
    }

    public void delDiscount() {
        discount.delFromDB();
        discount = null;
        programModel.discountID = "";
    }

    public void updateDiscount(String endDate, double discountPercentage) {
        discount.edit(endDate, discountPercentage);
    }


    //////////////////////// Discount controller ///////////////////////////

    private class Discount implements DB_Interface{

        public DiscountModel discountModel;

        public Discount (DiscountModel dm){
            this.discountModel = dm;
        }

        public Discount(String endDate, double discountPercentage){
            discountModel = new DiscountModel();
            discountModel.id = dbID("Discounts");
            discountModel.endDate = endDate;
            discountModel.discountPercentage = discountPercentage;
        }

        public String getId() {
            return discountModel.id;
        }

        public void setId(String id) {
            discountModel.id = id;
        }

        public String getEndDate() {
            return discountModel.endDate;
        }

        public void setEndDate(String endDate) {
            discountModel.endDate = endDate;
        }

        public double getDiscountPercentage() {
            return discountModel.discountPercentage;
        }

        public void setDiscountPercentage(double discountPercentage) {
            discountModel.discountPercentage = discountPercentage;
        }

        public void edit(String endDate, double discountPercentage) {
            this.discountModel.discountPercentage = discountPercentage;
            this.discountModel.endDate = endDate;
            updateToDB();
        }

        public void saveToDB(){
            dbRef = FirebaseDatabase.getInstance().getReference("Discounts");
            dbRef.child(this.discountModel.id).setValue(this.discountModel);
        }

        @Override
        public void updateToDB() {
            saveToDB();
        }

        public void getByID(@NonNull final SimpleCallback<Discount> finishedCallback, String id) {
            dbRef = FirebaseDatabase.getInstance().getReference();
            Query q = dbRef.child("Discounts").orderByChild("id").equalTo(id);
            final DiscountModel[] dm = new DiscountModel[1];
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        dm[0] = data.getValue(DiscountModel.class);
                        if (dm[0] == null)
                            finishedCallback.callback(null);
                        else
                            finishedCallback.callback(new Discount(dm[0]));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("DiscountController", "Failed to read value by ID.", databaseError.toException());
                }
            });
        }

        public void delFromDB() {
            dbRef = FirebaseDatabase.getInstance().getReference("Discounts");
            dbRef.child(this.discountModel.id).setValue(null);
        }
    }
}
