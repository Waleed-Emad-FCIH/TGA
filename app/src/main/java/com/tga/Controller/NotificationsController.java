package com.tga.Controller;

import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mada on 6/12/2018.
 */

public class NotificationsController extends AsyncTask<Long, Void, Void> {

    public final static String AUTH_KEY_FCM = "AIzaSyCmCOZBZR4VugM013zdD0Li8sxjs8VXEnI";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    @Override
    protected Void doInBackground(Long... params) {


        String token = FirebaseInstanceId.getInstance().getToken().toString();
        if (params[0]!=0){
            pushFCMNotification(token,params[0]);
        }else {
            pushFCMNotification(token,0);
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


    public static void pushFCMNotification(String userDeviceIdKey,long s) {
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
            if (s != 0) {
                info.put("body", "Welcome"); // Notification body
            } else {
                info.put("body", "Welcome"); // Notification body

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
}

