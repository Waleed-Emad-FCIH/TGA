package com.tga.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.models.AgentModel;
import com.tga.models.FeedbackModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class AgentController extends UserController {
    private AgentModel agentModel;
    
   public AgentController(String id, String email, String pass, String name, String phoneNo, String adrs,
                           String photo, String registrationNumber, ArrayList<String> myProgsID) {
        super(id, email, pass, name, phoneNo, adrs);
        agentModel.photo = photo;
        agentModel.registrationNumber = registrationNumber;
        agentModel.myProgramsID = myProgsID;
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

    public void getFinancialReport(){ }

    public void getProgramsReport(){ }

    public void writeFeedback(FeedbackController objView){

objView.addFeedback();
    /* no activity */
    }

}
