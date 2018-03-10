package com.tga.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tga.fragment.gettingAround.Driving;
import com.tga.fragment.gettingAround.OnArrival;
import com.tga.fragment.gettingAround.PublicTransportation;
import com.tga.fragment.gettingAround.TaxiService;
import com.tga.fragment.gettingAround.WalkingBiking;

/**
 * Created by Mada on 2/11/2018.
 */

public class GettingAroundAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public GettingAroundAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                OnArrival tab1 = new OnArrival();
                return tab1;
            case 1:
                PublicTransportation tab2 = new PublicTransportation();
                return tab2;
            case 2:
                TaxiService tab3 = new TaxiService();
                return tab3;
            case 3:
                Driving tab4 = new Driving();
                return tab4;
            case 4:
                WalkingBiking tab5 = new WalkingBiking();
                return tab5;
            default:
                OnArrival tab = new OnArrival();
                return tab;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}