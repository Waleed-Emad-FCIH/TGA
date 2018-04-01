package com.tga.model;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class Offers {

    private String title,price;
    private int image;

    public Offers(String title, String price, int image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String address) {
        this.title = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
