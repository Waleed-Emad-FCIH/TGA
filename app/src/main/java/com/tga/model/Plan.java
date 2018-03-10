package com.tga.model;

/**
 * Created by Mada on 2/8/2018.
 */

public class Plan {
    private String title,sites,shortInfo;
    private int imgMap,imgSite1,imgSite2,imgSite3;

    public Plan(String title, String sites, String shortInfo, int imgMap, int imgSite1, int imgSite2, int imgSite3) {
        this.title = title;
        this.sites = sites;
        this.shortInfo = shortInfo;
        this.imgMap = imgMap;
        this.imgSite1 = imgSite1;
        this.imgSite2 = imgSite2;
        this.imgSite3 = imgSite3;
    }

    public Plan() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public int getImgMap() {
        return imgMap;
    }

    public void setImgMap(int imgMap) {
        this.imgMap = imgMap;
    }

    public int getImgSite1() {
        return imgSite1;
    }

    public void setImgSite1(int imgSite1) {
        this.imgSite1 = imgSite1;
    }

    public int getImgSite2() {
        return imgSite2;
    }

    public void setImgSite2(int imgSite2) {
        this.imgSite2 = imgSite2;
    }

    public int getImgSite3() {
        return imgSite3;
    }

    public void setImgSite3(int imgSite3) {
        this.imgSite3 = imgSite3;
    }
}
