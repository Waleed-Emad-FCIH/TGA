package com.tga.Controller;

import com.tga.models.SupervisorModel;

/**
 * Created by root on 3/9/18.
 */

public class SupervisorController extends UserController {

    private SupervisorModel supervisorModel;

    public SupervisorController(String id, String email, String pass, String name, String phoneNo, String adrs) {
        super(id, email, pass, name, phoneNo, adrs);
    }

    public void getFinancialReport(){ }

    public void getPlacesReport(){ }

    public void getProgramsReport(){ }

    public void getTouristsReport(){ }

    public void getFeedbacks(){ }
}
