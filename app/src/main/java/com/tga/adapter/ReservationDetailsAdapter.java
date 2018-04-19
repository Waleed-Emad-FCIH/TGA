package com.tga.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tga.fragment.ReservaionDetails.AllPrices;
import com.tga.fragment.ReservaionDetails.Details;
import com.tga.fragment.ReservaionDetails.Reviews;
import com.tga.fragment.thingsToDo.InDoors;
import com.tga.fragment.thingsToDo.OutDoors;
import com.tga.fragment.thingsToDo.PlacesAZ;
import com.tga.fragment.thingsToDo.TopSpots;

/**
 * Created by Mada on 4/19/2018.
 */

public class ReservationDetailsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ReservationDetailsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllPrices tab1 = new AllPrices();
                return tab1;
            case 1:
                Details tab2 = new Details();
                return tab2;
            case 2:
                Reviews tab3 = new Reviews();
                return tab3;
            default:
                AllPrices tab = new AllPrices();
                return tab;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}