package com.tga.model;

/**
 * Created by Mada on 3/1/2018.
 */

public class HotelMdoel {
    private String reservationName,rate,price;
    private int reservationImg;

    public HotelMdoel() {
    }

    public HotelMdoel(String reservationName, String rate, String price, int reservationImg) {
        this.reservationName = reservationName;
        this.rate = rate;
        this.price = price;
        this.reservationImg = reservationImg;
    }

    public HotelMdoel(String reservationName, int reservationImg) {
        this.reservationName = reservationName;
        this.reservationImg = reservationImg;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getReservationImg() {
        return reservationImg;
    }

    public void setReservationImg(int reservationImg) {
        this.reservationImg = reservationImg;
    }
}
