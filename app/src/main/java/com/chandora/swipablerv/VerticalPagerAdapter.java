package com.chandora.swipablerv;

import android.graphics.Color;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public  class VerticalPagerAdapter extends FragmentStatePagerAdapter {
    public VerticalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public Fragment getItem(int position) {

//        switch (position) {
//            case 0:
                return ParentFragment.newInstance(position, Color.RED);
//            default:
//                // return a different Fragment class here
//                // if you want want a completely different layout
//                return ParentFragment.newInstance(position, Color.CYAN);
//        }
    }
}
