package com.tga.Controller;

public class SimpleSession {

    private static SimpleSession ourInstance = null;
    private Object userObj;
    private String userRole;

    public static SimpleSession getInstance(Object obj, String role) {
        if (ourInstance == null){
            ourInstance = new SimpleSession(obj, role);
        }
        return ourInstance;
    }

    private SimpleSession(Object obj, String role) {
        this.userObj = obj;
        this.userRole = role;
    }

    public Object getUserObj() {
        return userObj;
    }

    public String getUserRole() {
        return userRole;
    }
}
