package com.example.dima.locationtest.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.example.dima.locationtest.mvp.model.weather.CurrentWeather;

public interface WeatherView extends MvpView {
    void weatherView(CurrentWeather currentWeather);
    void dialogShow();
    void dialogDismiss();
    void error();
}
