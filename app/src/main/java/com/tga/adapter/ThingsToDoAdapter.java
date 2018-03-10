package com.tga.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tga.fragment.thingsToDo.InDoors;
import com.tga.fragment.thingsToDo.OutDoors;
import com.tga.fragment.thingsToDo.PlacesAZ;
import com.tga.fragment.thingsToDo.TopSpots;

/**
 * Created by Mada on 2/28/2018.
 */

public class ThingsToDoAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ThingsToDoAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TopSpots tab1 = new TopSpots();
                return tab1;
            case 1:
                InDoors tab2 = new InDoors();
                return tab2;
            case 2:
                OutDoors tab3 = new OutDoors();
                return tab3;
            case 3:
                PlacesAZ tab4 = new PlacesAZ();
                return tab4;

            default:
                TopSpots tab = new TopSpots();
                return tab;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}