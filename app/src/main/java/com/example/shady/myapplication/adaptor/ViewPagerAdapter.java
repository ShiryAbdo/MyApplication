package com.example.shady.myapplication.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shady.myapplication.fragment.DrugsTabFragment;

/**
 * Created by ShaDy on 27-Mar-17.
 */

class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new DrugsTabFragment();
    }

    @Override
    public int getCount() {
        return 0;
    }
}
