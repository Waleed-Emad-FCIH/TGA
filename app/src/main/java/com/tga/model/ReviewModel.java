package com.tga.model;



public class ReviewModel {


    private String authorName;
    private String message;
    private String rating;

    public ReviewModel() {
    }

    public ReviewModel(String authorName, String message, String rating) {
        this.authorName = authorName;
        this.message = message;
        this.rating = rating;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
