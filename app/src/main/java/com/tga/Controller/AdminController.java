package com.tga.Controller;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tga.fragment.Privacy;
import com.tga.models.AdminModel;
import com.tga.models.AgentModel;
import com.tga.models.PrivacyModel;
import com.tga.models.SupervisorModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class AdminController extends UserController implements DB_Interface {
    private AgentController agentController;
    private SupervisorController supervisorController;
    private FirebaseDatabase mRef = FirebaseDatabase.getInstance();


    public AdminController(String id, String email, String pass, String name, String phoneNo, String adrs) {
        super(id, email, pass, name, phoneNo, adrs);
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


    public void addAgent(){

//        ArrayList<String>x = new ArrayList<>();
//        x.add("3");
//        x.add("4");
//        agentController = new AgentController("1","agent.com","123456789","Agent", "01115558888","giza",
//                "image","520",x);

        agentController = new AgentController(agentController.getId(),agentController.getEmail(),agentController.getPassword(),agentController.getName(), agentController.getPhoneNumber(),agentController.getAddress(),
                agentController.getPhoto(),agentController.getRegistrationNumber(),agentController.getMyPrograms());
        DatabaseReference agent = mRef.getReference("agents");
        agent.child(agentController.getId()).setValue(agentController);

    }

    public void delAgent(String agentId){

        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot();
        Query applesQuery = db_node.child("agents").orderByChild("id").equalTo(agentId);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void addSupervisor(){

//        supervisorController = new SupervisorController("1","agent.com","123456789","Agent", "01115558888","giza");
        supervisorController = new SupervisorController(supervisorController.getId(),supervisorController.getEmail(),supervisorController.getPassword(),supervisorController.getName(),supervisorController.getPhoneNumber(),supervisorController.getAddress());
        DatabaseReference agent = mRef.getReference("supervisors");
        agent.child(supervisorController.getId()).setValue(supervisorController);

    }

    public void delSupervisor(String supervisorId){
        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot();
        Query applesQuery = db_node.child("supervisors").orderByChild("id").equalTo(supervisorId);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void editPrivacy(String cont){
        //get privacy object from db
        PrivacyModel privacyModel = new PrivacyModel();
        privacyModel.content = cont;
    }

    @Override
    public void saveToDB() {

    }

    @Override
    public void delFromDB() {

    }

    @Override
    public void updateToDB() {

    }
}