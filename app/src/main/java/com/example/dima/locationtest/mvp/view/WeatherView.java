package com.example.dima.locationtest.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.dima.locationtest.mvp.model.weather.CurrentWeather;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface WeatherView extends MvpView {
    void weatherView(CurrentWeather currentWeather);
    void dialogShow();
    void dialogDismiss();
    void error();
}
