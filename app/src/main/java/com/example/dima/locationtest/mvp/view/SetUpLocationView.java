package com.example.dima.locationtest.mvp.view;

import android.location.Location;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface SetUpLocationView extends MvpView{
    void setUpLocation();
    void displayLocation();
    void getLocation(Location location);
}
