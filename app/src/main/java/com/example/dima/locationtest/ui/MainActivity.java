package com.example.dima.locationtest.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.dao.HelperFactory;
import com.example.dima.locationtest.ui.adapter.FragmentAdapter;

import static com.example.dima.locationtest.Common.PERMISSIONS_REQUEST_CODE;

public class MainActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager mViewPager = findViewById(R.id.viewPager);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(adapter);

        TabLayout mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        HelperFactory.setHelper(getApplicationContext());
    }

}
