package com.tga.Controller;

import java.util.ArrayList;

/**
 * Created by root on 4/4/18.
 */

public interface DB_Interface {
    public void saveToDB(); // save the model object of the controller to DB
    public void delFromDB(); // delete the object from DB
    public void updateToDB(); // update the model after changes
    public ArrayList<Object> listAll();
}
