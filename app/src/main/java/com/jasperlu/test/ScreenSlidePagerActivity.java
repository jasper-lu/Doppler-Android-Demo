package com.jasperlu.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.jasperlu.doppler.Doppler;

/**
 * Created by Jasper on 3/24/2015.
 */
public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 4;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private Doppler doppler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        doppler = TheDoppler.getDoppler();
        doppler.start();

        //go to the doppler graph in 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Pager", "Going to next page");
                mPager.setCurrentItem(1);
            }
        }, 2500);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() <= 1) {
            // If the user is currently looking at the first or second step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
        }else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new IntroFragment();
                case 1:
                    return new GraphFragment();
                case 2:
                    return new DemoFragment();
                case 3:
                    return new EndFragment();
            }

            return new DemoFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        doppler.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doppler.start();
    }
}
