package com.tga.Controller;

import com.tga.models.ReportModel;

/**
 * Created by root on 3/9/18.
 */

public class ReportController {

    private ReportModel reportModel;

    public ReportController(String id, String subject, String date, String userID){
        this.reportModel = new ReportModel();
        reportModel.id = id;
        reportModel.subject = subject;
        reportModel.date = date;
        reportModel.userID = userID;
    }

    public void makeFinancialReport(){ }

    public void makePlacesReport(){ }

    public void makeProgramsReport(){ }

    public void makeTouristsReport(){ }

}
