package com.tga.model;

/**
 * Created by Mada on 2/28/2018.
 */

public class PlaceModel {
    private String title,rate,reivewsNum,stEnd,description;
    int plImg;

    public PlaceModel(String title, String rate, String reivewsNum, String stEnd, String description, int plImg) {
        this.title = title;
        this.rate = rate;
        this.reivewsNum = reivewsNum;
        this.stEnd = stEnd;
        this.description = description;
        this.plImg = plImg;
    }

    public PlaceModel(String title, int plImg) {
        this.title = title;
        this.plImg = plImg;
    }

    public PlaceModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReivewsNum() {
        return reivewsNum;
    }

    public void setReivewsNum(String reivewsNum) {
        this.reivewsNum = reivewsNum;
    }

    public String getStEnd() {
        return stEnd;
    }

    public void setStEnd(String stEnd) {
        this.stEnd = stEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlImg() {
        return plImg;
    }

    public void setPlImg(int plImg) {
        this.plImg = plImg;
    }
}
