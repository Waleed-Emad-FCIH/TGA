package com.tga.Controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tga.models.ProgramModel;
import com.tga.models.ReportModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 3/9/18.
 */

public class ReportController {
    DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Programs");
    DatabaseReference touristReference =FirebaseDatabase.getInstance().getReference().child("Programs");
ProgramController program;
    private ReportModel reportModel;

    public ReportController() {
    }

    public ReportController(String id, String subject, String date, String userID){
        this.reportModel = new ReportModel();
        reportModel.id = id;
        reportModel.subject = subject;
        reportModel.date = date;

        reportModel.userID = userID;
    }

    public void makeFinancialReport()
    {

    }

    public void makePlacesReport( String AgentId){
    }

    public ArrayList<ProgramModel> makeProgramsReport(String AgentId){
        ArrayList<String>myProgramsIds= program.ListProgramsOfAgent(AgentId);
        ArrayList<ProgramModel> myPrograms=program.GetSomePrograms(myProgramsIds);
        for (ProgramModel pM :myPrograms)
        {
            pM.registeredTouristsID= program.getNames(pM.registeredTouristsID);

        }
        return myPrograms;

    }

    public void makeTouristsReport(){ }

}
