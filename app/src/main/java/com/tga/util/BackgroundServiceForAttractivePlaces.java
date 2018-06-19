package com.tga.util;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.*;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.*;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.tga.Controller.NotificationsController;
import com.tga.R;

import java.util.Locale;

/**
 * Created by Mada on 6/16/2018.
 */

public class BackgroundServiceForAttractivePlaces extends Service implements LocationListener {


    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;


    double latitude;
    double longitude;
    protected LocationManager locationManager;






    private double  lat[]= {30.0052663,31.18623264,30.045915,30.0477386,30.0057322,30.0406319,30.045688,30.0322791,30.028691,30.0287015,30.0478468,30.0298604,30.0101215,29.98155515000001};
    private double lng[]= {31.2301826,30.0317241,31.2242898,31.2622538,31.2310336,31.2647327,31.2626851,31.2561704,31.2493941,31.25991059999999,31.2336493,31.2611055,31.23313679999999,31.13530367989272};

    private String name[]= {"The Hanging Church","Cairo Tower","Khan el-Khalili",
            "Ben Ezra Synagogue", "Al Azhar Park","Al-Azhar Mosque",
            "Mosque-Madrassa of Sultan Hassan", "Mosque of Ibn Tulun","Mosque of Muhammad Ali",
            "Egyptian Museum Cairo","Salah El Din Al Ayouby Citadel","Masjid Amr Ibn El Aas","The Great Pyramid at Giza"};

    private String id[]= {"ChIJn3WoRhBHWBQRuhsCgung4tQ","ChIJpVgdqJpAWBQR_tZY0ny_5mw","ChIJD1gOLOtAWBQREX0CYeTgYRc",
            "ChIJd37-VBBHWBQRXjbwRwh6nHE", "ChIJDVvpPMZAWBQRFX-IsCxe70s","ChIJ9SH986JAWBQR6FIeS8dSZ2c",
            "ChIJdRfpCqxAWBQR5IwPTJC5xnQ", "ChIJp0luK01HWBQRUtnkQJOsgkA","ChIJn1b3gKpAWBQRbsg1WfPe1NA",
            "ChIJDSAkL8RAWBQR447lPPV4wRE", "ChIJY-3CkKxAWBQRby736eSW2y8","ChIJI5Co-XtHWBQRawUwEcSA844","ChIJGymPrIdFWBQRJCSloj8vDIE"};



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
//        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

//        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
//        }

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                getLocation();
                 double nearest[] = {15,15,15,15,15,15,15,15,15,15,15,15,15,15};
                 int index[] = {15,15,15,15,15,15,15,15,15,15,15,15,15,15};
                int x = 0;
                for (int i = 0;i < lat.length;i++){
                   double z = distance(latitude, longitude,   lat[i], lng[i]);
                    if (z < 0.5) { // if distance < 0.1

                        //   launch the notification
//                        new NotificationsController().execute(name[i]);

                        nearest[x]=z;
                        index[x]= i;
                        x++;
                    }

                }


                if (nearest!= null&& nearest.length>0&&nearest[0]!=15.0) {
                    int k = index[findMinIdx(nearest)];
                    new NotificationsController().execute("You are nearby "+name[k],id[k]);
                }



                handler.postDelayed(runnable, 1800000);
//                Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();

            }
        };

        handler.postDelayed(runnable, 10000);
    }


    public int findMinIdx(double[] numbers) {
        if (numbers == null || numbers.length == 0) return -1; // Saves time for empty array
        // As pointed out by ZouZou, you can save an iteration by assuming the first index is the smallest
        double minVal = numbers[0]; // Keeps a running count of the smallest value so far
        int minIdx = 0; // Will store the index of minVal
        for(int idx=1; idx<numbers.length; idx++) {
            if(numbers[idx] < minVal) {
                minVal = numbers[idx];
                minIdx = idx;
            }
        }
        return minIdx;
    }




    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometers

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist;
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        handler.removeCallbacks(runnable);
//        Intent i = new Intent(this, BackgroundServiceForAttractivePlaces.class);
//        stopService(i);
//        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
//        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
//        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}