package com.tga.fragment.gettingAround;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tga.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnArrival extends Fragment {

    private TextView discTaxi,discRide,discPrivate;
    private boolean isTextViewClicked = false,isTextViewClicked2 = false,isTextViewClicked3 = false;

    public OnArrival() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_on_arrival, container, false);

        discTaxi = (TextView)v.findViewById(R.id.discTaxi);
        discRide = (TextView)v.findViewById(R.id.discRide);
        discPrivate = (TextView)v.findViewById(R.id.discPrivate);
        discTaxi.setText("Taxi"+" \n\n" + "Taxis wait in line across the road outside arrivals. A ride to the city centers costs 60-80 EGP and takes 30-40 minutes in regular traffic.");
        discTaxi.setMaxLines(1);
        discTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    //This will shrink textview to 2 lines if it is expanded.
                    discTaxi.setMaxLines(1);
                    isTextViewClicked = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    discTaxi.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                    discRide.setMaxLines(1);
                    isTextViewClicked2 = false;
                    discPrivate.setMaxLines(1);
                    isTextViewClicked3 = false;

                }
            }
        });
        discRide.setText("Ride service"+" \n\n" + "Ride Service like UBER and CAREEM are an alternative to taxis");
        discRide.setMaxLines(1);
        discRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked2) {
                    //This will shrink textview to 2 lines if it is expanded.
                    discRide.setMaxLines(1);
                    isTextViewClicked2 = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    discRide.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked2 = true;
                    discPrivate.setMaxLines(1);
                    isTextViewClicked3 = false;
                    discTaxi.setMaxLines(1);
                    isTextViewClicked = false;
                }
            }
        });
        discPrivate.setText("Private Car"+" \n\n" + "Private car services offer a more comfortable ride than the average taxi. it cost about 150 EGP for a ride to the center of the city");
        discPrivate.setMaxLines(1);
        discPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked3) {
                    //This will shrink textview to 2 lines if it is expanded.
                    discPrivate.setMaxLines(1);
                    isTextViewClicked3 = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    discPrivate.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked3 = true;
                    discTaxi.setMaxLines(1);
                    isTextViewClicked = false;
                    discRide.setMaxLines(1);
                    isTextViewClicked2 = false;
                }
            }
        });



        return v;
    }

}
