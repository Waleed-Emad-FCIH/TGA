package com.tga.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tga.Activity.MainActivity;
import com.tga.Activity.PlaceDetails;
import com.tga.Activity.ShowMyPlans;
import com.tga.R;

import org.json.JSONObject;

import java.util.Map;


public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCMCallbackService";
    PendingIntent pendingIntent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.d(TAG, "From:" + remoteMessage.getFrom());
        //Log.d(TAG, "Message Body:" + remoteMessage.getNotification().getBody());
        try
        {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON_OBJECT", object.toString());
        }catch (Exception e){

        }
        sendNotification(remoteMessage.getNotification(),remoteMessage.getFrom());
    }

    private void sendNotification(RemoteMessage.Notification notification,String placeId) {
        int color = getResources().getColor(R.color.colorPrimary);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (notification.getTag().equals("0")){
            Intent intent = new Intent(this, ShowMyPlans.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }else {
            Intent intent = new Intent(this, PlaceDetails.class);
            intent.putExtra("id",notification.getTag());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo_icon)
                .setColor(color)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getBody()))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}