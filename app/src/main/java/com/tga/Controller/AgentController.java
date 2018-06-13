package com.tga.Controller;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.AgentModel;
import com.tga.models.FeedbackModel;
import com.tga.models.ProgramModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class AgentController extends UserController implements DB_Interface{
    private AgentModel agentModel;
    private static DatabaseReference dbRef;
    
   public AgentController(String id, String email, String pass, String name, String phoneNo, String adrs,
                           String photo, String registrationNumber, ArrayList<String> myProgsID) {
       super(id, email, pass, name, phoneNo, adrs);
       agentModel = new AgentModel();
       agentModel.id = id;
       agentModel.email = email;
       agentModel.password = pass;
       agentModel.name = name;
       agentModel.phoneNumber = phoneNo;
       agentModel.address = adrs;
       agentModel.photo = photo;
       agentModel.registrationNumber = registrationNumber;
       agentModel.myProgramsID = myProgsID;
    }

    public AgentController(String id, String email, String pass, String name, String phoneNo, String adrs,
                           String photo, String registrationNumber, ArrayList<String> myProgsID,boolean state) {
        super(id, email, pass, name, phoneNo, adrs);
        agentModel = new AgentModel();
        agentModel.id = id;
        agentModel.email = email;
        agentModel.password = pass;
        agentModel.name = name;
        agentModel.phoneNumber = phoneNo;
        agentModel.address = adrs;
        agentModel.photo = photo;
        agentModel.registrationNumber = registrationNumber;
        agentModel.myProgramsID = myProgsID;
        agentModel.State=state;
    }

    private AgentController(AgentModel model) {
       super(model.id, model.email, model.password, model.name, model.phoneNumber, model.address);
        agentModel.photo = model.photo;
        agentModel.registrationNumber = model.registrationNumber;
        agentModel.myProgramsID = model.myProgramsID;
    }

    public boolean getState() {
        return agentModel.State;
    }

    public String getPhoto() {
        return agentModel.photo;
    }

    public void setPhoto(String photo) {
        agentModel.photo = photo;
    }

    public String getRegistrationNumber() {
        return agentModel.registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        agentModel.registrationNumber = registrationNumber;
    }

    public ArrayList<String> getMyPrograms(){
        return agentModel.myProgramsID;
    }

    public void addProgram(String progID){
        agentModel.myProgramsID.add(progID);
    }

    public void delProgram(String progID){
        agentModel.myProgramsID.remove(progID);
    }

    public void editMyProgram(String programID, final String title, final String desc,
                              final String startDate, final String endDate, final String hotelName) {
        ProgramController.getByID(new SimpleCallback<ProgramController>() {
            @Override
            public void callback(ProgramController pc) {
                pc.editProgram(title, desc, startDate, endDate, hotelName);
                pc.updateToDB();
            }
        }, programID);
    }

    public void getFinancialReport(){ }

    public void getProgramsReport(){ }

    public void writeFeedback(FeedbackController objView){
        objView.addFeedback();
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

    @Override
    public void saveToDB() {
        dbRef = FirebaseDatabase.getInstance().getReference("Agents");
        dbRef.child(this.getId()).setValue(this.agentModel);
    }

    @Override
    public void delFromDB() {
        dbRef = FirebaseDatabase.getInstance().getReference("Agents");
        dbRef.child(this.getId()).setValue(null);
    }

    @Override
    public void updateToDB() {
        saveToDB();
    }

    /*public static ArrayList<AgentController> listAll(){
        final ArrayList<AgentController> list = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference("Agents");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AgentModel model = snapshot.getValue(AgentModel.class);
                    if (model != null)
                        list.add(new AgentController(model));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("AgentController", "Failed to read list values.", databaseError.toException());
            }
        });
        return list;
    }*/

    public static void getByID(@NonNull final SimpleCallback<AgentController> finishedCallback, String id){
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        Query q = dRef.child("Agents").orderByChild("id").equalTo(id);
        final AgentModel[] am = new AgentModel[1];
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    am[0] = data.getValue(AgentModel.class);
                }
                if (am[0] == null)
                    finishedCallback.callback(null);
                else
                    finishedCallback.callback(new AgentController(am[0]));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("AgentController", "Failed to read value by ID.", databaseError.toException());
            }
        });
    }


    /*public static AgentController getByID(String id){
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        Query q = dRef.child("Agents").orderByChild("id").equalTo(id);
        final AgentModel[] am = new AgentModel[1];
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                    am[0] = dataSnapshot.getValue(AgentModel.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("AgentController", "Failed to read value by ID.", databaseError.toException());
            }
        });
        if (am[0] == null)
            return null;
        return new AgentController(am[0]);
    }*/
}
