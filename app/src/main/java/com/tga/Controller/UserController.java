package com.tga.Controller;

import com.tga.models.UserModel;

/**
 * Created by root on 3/9/18.
 * new test
 */

public abstract class UserController {

    private UserModel userModel;

    public UserController(String id, String email, String pass, String name,
                     String phoneNo, String adrs){
        this.userModel = new UserModel();
        userModel.id =id;
        userModel.email = email;
        userModel.password = pass;
        userModel.name = name;
        userModel.phoneNumber = phoneNo;
        userModel.address = adrs;
    }

    public String getId() {
        return userModel.id;
    }

    public String getEmail() {
        return userModel.email;
    }

    public void setEmail(String email) {
        userModel.email = email;
    }

    public String getPassword() {
        return userModel.password;
    }

    public void setPassword(String password) {
        userModel.password = password;
    }

    public String getName() {
        return userModel.name;
    }

    public void setName(String name) {
        userModel.name = name;
    }

    public String getPhoneNumber() {
        return userModel.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        userModel.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return userModel.address;
    }

    public void setAddress(String address) {
        userModel.address = address;
    }

    public abstract void login(String email, String pass);

    public abstract void logout();

    public abstract void editProfile ();

    public abstract void search();

}
