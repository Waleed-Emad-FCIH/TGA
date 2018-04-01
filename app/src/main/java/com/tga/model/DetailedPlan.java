package com.tga.model;

/**
 * Created by Mada on 2/8/2018.
 */

public class DetailedPlan {
    private String title,open,info,timeByFoot;

    public DetailedPlan() {
    }

    public DetailedPlan(String title, String open, String info, String timeByFoot) {
        this.title = title;
        this.open = open;
        this.info = info;
        this.timeByFoot = timeByFoot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTimeByFoot() {
        return timeByFoot;
    }

    public void setTimeByFoot(String timeByFoot) {
        this.timeByFoot = timeByFoot;
    }
}
