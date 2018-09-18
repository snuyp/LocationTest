package com.example.dima.locationtest.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.locationtest.Common;
import com.example.dima.locationtest.mvp.model.weather.CurrentWeather;
import com.example.dima.locationtest.mvp.view.WeatherView;
import com.example.dima.locationtest.remote.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {
    private WeatherService weatherService = Common.getWeatherService();

    public void loadWeather(double lat,double lon)
    {
        getViewState().dialogShow();

        weatherService.getToday(lat,lon,Common.units,Common.WEATHER_API_KEY).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if(response.body() != null)
                {
                    getViewState().weatherView(response.body());
                }
                else
                {
                    getViewState().error();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
    }
}
