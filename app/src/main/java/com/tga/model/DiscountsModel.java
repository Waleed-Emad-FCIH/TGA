package com.tga.model;

/**
 * Created by Mada on 3/1/2018.
 */

public class DiscountsModel {
    private String offer,title,companyName;
    private int offerImg;

    public DiscountsModel(String offer, String title, String companyName, int offerImg) {
        this.offer = offer;
        this.title = title;
        this.companyName = companyName;
        this.offerImg = offerImg;
    }

    public DiscountsModel() {
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getOfferImg() {
        return offerImg;
    }

    public void setOfferImg(int offerImg) {
        this.offerImg = offerImg;
    }
}
