package com.tga.model;

import java.util.List;

/**
 * Created by Mada on 4/29/2018.
 */

public class place {
    private String name,place_id;
    private float rating;
    private List<photos> photos;
    private String uid;
    private String imgLink;
    private String next_page_token;



    public place() {
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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


