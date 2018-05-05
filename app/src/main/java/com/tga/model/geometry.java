package com.tga.model;

/**
 * Created by Mada on 5/5/2018.
 */

public class geometry {

    private location location;

    public geometry(geometry.location location) {
        this.location = location;
    }

    public geometry() {
    }

    public geometry.location getLocation() {
        return location;
    }

    public void setLocation(geometry.location location) {
        this.location = location;
    }

    public class location{
        private String lat,lng;

        public location(String lat, String lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public location() {
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }
}
