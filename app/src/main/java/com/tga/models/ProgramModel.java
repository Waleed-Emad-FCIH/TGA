package com.tga.models;

import java.util.ArrayList;
import java.util.Date;

public class ProgramModel {
    public String id ;
    public String title ;
    public ArrayList<String> placesID ;
    public String description ;
    public String startDate ;
    public String endDate ;
    public String hotelName ;
    public String discountID;
    public int rate;
    public int hitRate; // # of people rated this program
    public ArrayList<String> reviews;
    public ArrayList<String> registeredTouristsID;

}

