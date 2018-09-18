package com.example.dima.locationtest.mvp.view;

import android.location.Location;

import com.arellomobile.mvp.MvpView;

public interface SetUpLocationView extends MvpView{
    void setUpLocation();
    void displayLocation();
    void getLocation(Location location);
}
