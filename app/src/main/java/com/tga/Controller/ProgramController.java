package com.tga.Controller;

import com.tga.models.DiscountModel;
import com.tga.models.ProgramModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class ProgramController {

    private ProgramModel programModel;
    private Discount discount;

    public ProgramController(String id, String title, ArrayList<String> placesID, String description,
                        String startDate, String endDate, String hotelName, String discountID,
                             double rate, ArrayList<String> reviews, ArrayList<String> regisID){
        programModel.id = id;
        programModel.title = title;
        programModel.placesID = placesID;
        programModel.description = description;
        programModel.startDate = startDate;
        programModel.endDate = endDate;
        programModel.hotelName = hotelName;
        programModel.discountID = discountID;
        programModel.rate = rate;
        programModel.reviews = reviews;
        programModel.registeredTouristsID = regisID;
    }

    public String getId() {
        return programModel.id;
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

    public void addPlaceID(String placeID) {
        programModel.placesID.add(placeID);
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

    public void setDiscount(){
        discount = new Discount();
    }

    public String getDiscountID() {
        return programModel.discountID;
    }

    public void delDiscount() {
        discount.del();
        discount = null;
    }

    public void editDiscount() { discount.edit();}

    public void editProgram() { }

    public void delProgram() { }

    public void rate() { }

    public double getRate(){
        return programModel.rate;
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

    private class Discount {

        public DiscountModel discountModel;

        public Discount(){}
        public Discount(String id, String endDate, String programId, double discountPercentage){
            discountModel.id = id;
            discountModel.endDate = endDate;
            discountModel.discountPercentage = discountPercentage;
            discountModel.programId = programId;
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

        public double getDiscountPersentage() {
            return discountModel.discountPercentage;
        }

        public void setDiscountPersentage(double discountPersentage) {
            discountModel.discountPercentage = discountPersentage;
        }

        public String getProgramId() {
            return discountModel.programId;
        }

        public void setProgramId(String programId) {
            discountModel.programId = programId;
        }

        public void edit() { }

        public void del() { }
    }
}
