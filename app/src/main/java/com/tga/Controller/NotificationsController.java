package com.tga.Controller;

import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tga.models.PlanModel;
import com.tga.models.TouristPlan;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mada on 6/12/2018.
 */

public class NotificationsController extends AsyncTask<String, Void, Void> {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public final static String AUTH_KEY_FCM = "AIzaSyCmCOZBZR4VugM013zdD0Li8sxjs8VXEnI";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    private static DatabaseReference dbRef;
    TouristPlan touristPlan;
    private String uid = mAuth.getCurrentUser().getUid();
    public NotificationsController() {
    }

    private NotificationsController(TouristPlan touristPlan) {
        this.touristPlan = touristPlan;
    }

    @Override
    protected Void doInBackground(String... params) {


        String token = FirebaseInstanceId.getInstance().getToken().toString();
        if (params[0]==null){
            pushFCMNotification(token,params[0],"You are nearby attractive place",params[1]);
        }else {
            pushFCMNotification(token,params[0], params[0],params[1]);
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    public static void pushFCMNotification(String userDeviceIdKey,String s,String body,String placeId) {
        try {
            String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
            String FMCurl = API_URL_FCM;

            URL url = new URL(FMCurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + authKey);
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("to", userDeviceIdKey.trim());
            JSONObject info = new JSONObject();
            info.put("title", "TGA");   // Notification title
            if (s == null) {
                info.put("body", "Welcome"); // Notification body
            } else {
                info.put("body", body);
                info.put("tag",placeId);
                // Notification body

            }
            json.put("notification", info);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            int responseCode = conn.getResponseCode();
            String respMsg = conn.getResponseMessage();
            String test = "";
        } catch (Exception ex) {
//            Log.d("errors", "pushFCMNotification: " + ex.getMessage());
//            Log.e("errors", ex.getMessage());
        }
    }





    public void checkPlan(SimpleCallback<ArrayList<TouristPlan>> simpleCallback){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<TouristPlan> touristPlans = new ArrayList<>();

        Query query1 = database.getReference("touristsPlans").orderByChild("userId").equalTo(uid);
        query1.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TouristPlan touristModel = snapshot.getValue(TouristPlan.class);
                        touristPlans.add(touristModel);
                    }
                    simpleCallback.callback(touristPlans);
                } catch (Exception e) {

                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                simpleCallback.callback(touristPlans);
            }
        });

    }

    public void saveToDB(){

    }

    public void updateToDB(String id ,boolean notified,String planDate,String planID) {
        TouristPlan touristPlan = new TouristPlan(planID,notified,planDate,id,uid);
        dbRef = FirebaseDatabase.getInstance().getReference("touristsPlans");
        dbRef.child(id).setValue(touristPlan);
    }
}

