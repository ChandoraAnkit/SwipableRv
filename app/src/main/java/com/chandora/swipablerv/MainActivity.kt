package com.chandora.swipablerv

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ToggleVerticalViewPagerScrolling {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(
                ContextCompat.getColor(this,android.R.color.black))
            window.setNavigationBarColor(ContextCompat.getColor(this,android.R.color.black))
        }


//        val mSnapHelper: SnapHelper = PagerSnapHelper()
//        mSnapHelper.attachToRecyclerView(recycler_view)
//
//        recycler_view.adapter = ItemAdapter()

        var mAdapter =
            VerticalPagerAdapter(
                getSupportFragmentManager()
            );
        viewpager.setAdapter(mAdapter);
//
//        viewpager.currentItem

    }

    override fun trigger(page: Int) {
        if (page == 1) {
            viewpager.setOnTouchListener { v, event -> true }
        } else {
            viewpager.setOnTouchListener { v, event -> false }
        }
        }
}