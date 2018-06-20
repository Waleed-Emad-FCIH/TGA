package com.tga.models;

import java.util.List;

/**
 * Created by Mada on 5/5/2018.
 */

public class placeDetailsModel {
    private String name,place_id;
    private float rating;
    private List<photos> photos;
    private List<reviews> reviews;
    private com.tga.models.geometry geometry;
    private opening_hours opening_hours;
    private String international_phone_number;
    private String website;

    public placeDetailsModel() {
    }

    public placeDetailsModel(String name, String place_id, float rating, List<com.tga.models.photos> photos, List<com.tga.models.reviews> reviews, com.tga.models.geometry geometry, placeDetailsModel.opening_hours opening_hours, String international_phone_number, String website) {
        this.name = name;
        this.place_id = place_id;
        this.rating = rating;
        this.photos = photos;
        this.reviews = reviews;
        this.geometry = geometry;
        this.opening_hours = opening_hours;
        this.international_phone_number = international_phone_number;
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
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

    public List<com.tga.models.photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<com.tga.models.photos> photos) {
        this.photos = photos;
    }

    public List<com.tga.models.reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<com.tga.models.reviews> reviews) {
        this.reviews = reviews;
    }

    public com.tga.models.geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(com.tga.models.geometry geometry) {
        this.geometry = geometry;
    }

    public placeDetailsModel.opening_hours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(placeDetailsModel.opening_hours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public class opening_hours {
        private boolean open_now;
        private String[] weekday_text;

        public opening_hours(boolean open_now, String[] weekday_text) {
            this.open_now = open_now;
            this.weekday_text = weekday_text;
        }

        public boolean isOpen_now() {
            return open_now;
        }

        public void setOpen_now(boolean open_now) {
            this.open_now = open_now;
        }

        public String[] getWeekday_text() {
            return weekday_text;
        }

        public void setWeekday_text(String[] weekday_text) {
            this.weekday_text = weekday_text;
        }
    }
}



