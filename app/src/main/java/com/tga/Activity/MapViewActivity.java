package com.tga.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

        //seattle coordinates
        ArrayList<LatLng> latLngs = (ArrayList<LatLng>) getIntent().getExtras().get("latLngs");
        for (int i = 0 ;i<latLngs.size() ; i++)
        {

            mMap.addMarker(new MarkerOptions().position(latLngs.get(i)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs.get(i)));
        }
        mMap.setMinZoomPreference(5);
        mMap.setMaxZoomPreference(20);

    }

}
