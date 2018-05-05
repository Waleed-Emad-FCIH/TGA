package com.tga.model;

import java.util.List;

/**
 * Created by Mada on 4/29/2018.
 */

public class place {
    private String name,place_id;
    private float rating;
    private List<photos> photos;



    public place() {
    }

    public place(String name, String place_id, float rating, List<com.tga.model.photos> photos) {
        this.name = name;
        this.place_id = place_id;
        this.rating = rating;
        this.photos = photos;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<com.tga.model.photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<com.tga.model.photos> photos) {
        this.photos = photos;
    }

}


