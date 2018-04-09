package com.tga.Controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.AgentModel;
import com.tga.models.FeedbackModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class AgentController extends UserController implements DB_Interface {
    private AgentModel agentModel;
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();


    public AgentController(String id, String email, String pass, String name, String phoneNo, String adrs,
                           String photo, String registrationNumber, ArrayList<String> myProgsID) {
        super(id, email, pass, name, phoneNo, adrs);
        agentModel.photo = photo;
        agentModel.registrationNumber = registrationNumber;
        agentModel.myProgramsID = myProgsID;
    }

    public void saveToDB() {
        DatabaseReference reference =mRef.getReference().child("Agents"); // name of table ?
        reference.child("programsID").setValue(agentModel.myProgramsID);
    }
    public void delFromDB(){
        DatabaseReference reference =mRef.getReference().child("Agents"); // name of table ?
        reference.child(agentModel.id).setValue(null);

    }
    public void updateToDB(){
        DatabaseReference dRef = mRef.getReference("Agents"); // name of table ?
        agentModel.id = dRef.push().getKey();
        dRef.child(agentModel.id).setValue(agentModel);
    }


    public ArrayList<AgentModel> listAll() {
        final DatabaseReference fRef = mRef.getReference("Agents"); //name of table
        final ArrayList<AgentModel> agentModels = new ArrayList<>();

        fRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AgentModel agentModel = snapshot.getValue(AgentModel.class);
                    agentModels.add(agentModel);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return agentModels;

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
       saveToDB();
    }

    public void delProgram(String progID){
        agentModel.myProgramsID.remove(progID);
        saveToDB();
    }

    public void getFinancialReport(){} // wait implement report controller

    public void getProgramsReport(){}  // wait implement report controller

    public void writeFeedback(FeedbackController objView){

objView.addFeedback();
    /* no activity */
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

}
