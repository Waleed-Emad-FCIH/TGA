package com.tga.fragment.gettingAround;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tga.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaxiService extends Fragment {


    public TaxiService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taxi, container, false);
    }

}
