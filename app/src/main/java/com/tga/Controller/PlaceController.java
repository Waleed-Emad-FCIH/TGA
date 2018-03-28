package com.tga.Controller;

import com.tga.models.PlaceModel;

import java.util.ArrayList;

/**
 * Created by root on 3/9/18.
 */

public class PlaceController {
    private PlaceModel placeModel;

    public PlaceController(String id, ArrayList<String> photos, String openTime, String closeTime,
                      String location, double rate, ArrayList<String> reviews){
        placeModel.id = id;
        placeModel.photos = photos;
        placeModel.openTime = openTime;
        placeModel.closeTime = closeTime;
        placeModel.location = location;
        placeModel.rate = rate;
        placeModel.reviews = reviews;
    }

    public String getId() {
        return placeModel.id;
    }

    public ArrayList<String> getPhotos() {
        return placeModel.photos;
    }

    public void addPhoto(String photo) {
        placeModel.photos.add(photo);
    }

    public String getOpenTime() {
        return placeModel.openTime;
    }

    public void setOpenTime(String openTime) {
        placeModel.openTime = openTime;
    }

    public String getCloseTime() {
        return placeModel.closeTime;
    }

    public void setCloseTime(String closeTime) {
        placeModel.closeTime = closeTime;
    }

    public String getLocation() {
        return placeModel.location;
    }

    public void setLocation(String location) {
        placeModel.location = location;
    }

    public void delPlace(){ }

    public void editPlace() { }

    public void ratePlace(){ }

    public double getRate(){
        return placeModel.rate;
    }

    public void addReview(String review){
        placeModel.reviews.add(review);
    }

    public ArrayList<String> getReviews(){
        return placeModel.reviews;
    }

}
