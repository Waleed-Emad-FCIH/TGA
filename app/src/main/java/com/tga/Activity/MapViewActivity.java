package com.tga.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tga.R;

import java.util.ArrayList;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<LatLng> latLngs = (ArrayList<LatLng>) getIntent().getExtras().get("latLngs");

        GoogleDirection.withServerKey("AIzaSyA02qeaptiL2YJ2P9CjHRrLhkkzO3cL7NM")
                .from(latLngs.get(0))
                .to(latLngs.get(latLngs.size()-1)).execute(new DirectionCallback() {
            @Override
            public void onDirectionSuccess(Direction direction, String rawBody) {
                Route route = direction.getRouteList().get(0);
                Leg leg= route.getLegList().get(0);
                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED);
                mMap.addPolyline(polylineOptions);
                //seattle coordinates
                for (int i = 0 ;i<latLngs.size() ; i++)
                {

                    mMap.addMarker(new MarkerOptions().position(latLngs.get(i)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs.get(i)));
                }
                mMap.setMinZoomPreference(5);
                mMap.setMaxZoomPreference(20);
            }

            @Override
            public void onDirectionFailure(Throwable t) {

            }
        });



    }

}
