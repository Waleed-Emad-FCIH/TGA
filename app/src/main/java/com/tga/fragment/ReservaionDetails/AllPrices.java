package com.tga.fragment.ReservaionDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tga.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllPrices extends Fragment {


    public AllPrices() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_prices, container, false);
    }

}
