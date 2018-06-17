package com.tga.Controller;

public class SimpleSession {

    private static SimpleSession ourInstance;
    public static int TOURIST_ROLE = 0;
    public static int AGENT_ROLE = 1;
    private Object userObj;
    private int userRole;

    public static SimpleSession getInstance() {
        if (ourInstance == null){
            ourInstance = new SimpleSession();
        }
        return ourInstance;
    }

    public static boolean isNull(){
        return (ourInstance == null);
    }

    private SimpleSession() {
    }

    public static void destroySession(){
        ourInstance = null;
    }

    public Object getUserObj() {
        return userObj;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserObj(Object userObj) {
        this.userObj = userObj;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}
