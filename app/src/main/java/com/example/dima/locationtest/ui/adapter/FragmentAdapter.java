package com.example.dima.locationtest.ui.adapter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dima.locationtest.R;
import com.example.dima.locationtest.ui.fragment.DataFragment;
import com.example.dima.locationtest.ui.fragment.WeatherFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WeatherFragment.getInstance();
            case 1:
                return DataFragment.getInstance();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.weather);
            case 1:
                return context.getString(R.string.data);
        }
        return "";
    }


    @Override
    public int getCount() {
        return 2;
    }
}