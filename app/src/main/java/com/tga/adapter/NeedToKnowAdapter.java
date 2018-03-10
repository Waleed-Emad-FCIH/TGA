package com.tga.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tga.fragment.needToKnow.HealthAndEmerg;
import com.tga.fragment.needToKnow.Money;
import com.tga.fragment.needToKnow.Shopping;

/**
 * Created by Mada on 2/21/2018.
 */

public class NeedToKnowAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public NeedToKnowAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Shopping tab1 = new Shopping();
                return tab1;
            case 1:
                Money tab2 = new Money();
                return tab2;
            case 2:
                HealthAndEmerg tab3 = new HealthAndEmerg();
                return tab3;

            default:
                Shopping tab = new Shopping();
                return tab;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}