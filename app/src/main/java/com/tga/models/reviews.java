package com.tga.models;

/**
 * Created by Mada on 5/5/2018.
 */

public class reviews {
    private String author_name,profile_photo_url,text;
    private float rating;

    public reviews() {
    }

    public reviews(String author_name, String profile_photo_url, String text, float rating) {
        this.author_name = author_name;
        this.profile_photo_url = profile_photo_url;
        this.text = text;
        this.rating = rating;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
