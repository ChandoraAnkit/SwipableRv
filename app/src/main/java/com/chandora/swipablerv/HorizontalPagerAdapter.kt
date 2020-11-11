package com.chandora.swipablerv

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * Created by Ankit
 */


class HorizontalPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
            return ChildFragment.newInstance("sd", "sd")
    }

    override fun getCount(): Int {
       return  2
    }
}