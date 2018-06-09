package com.tga.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by asus pc on 2/28/2018.
 */

public class PostModel {
    public String id;
    public String content;
    public long date;
    public String userId;
    public ArrayList<String> commentsID;
    public int likes;
    public ArrayList<String> likesID;


}
