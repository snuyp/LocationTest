package com.example.dima.locationtest.mvp.presenter;

import android.content.Context;
import android.location.Location;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.locationtest.Common;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.CurrentWeather;
import com.example.dima.locationtest.mvp.model.weather.dao.HelperFactory;
import com.example.dima.locationtest.mvp.model.weather.db.DataCache;
import com.example.dima.locationtest.mvp.model.weather.db.WeatherData;
import com.example.dima.locationtest.mvp.view.WeatherView;
import com.example.dima.locationtest.Interface.WeatherService;

import java.sql.SQLException;
import java.util.Date;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {
    private WeatherService weatherService = Common.getWeatherService();
    private CurrentWeather cw;

    public void loadWeather(final double lat, final double lon) {
        if (cw == null)
            getViewState().dialogShow();
            weatherService.getToday(lat, lon, Common.units, Common.WEATHER_API_KEY).enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    if (response.body() != null) {
                        cw = response.body();

                        //add to bd
                        WeatherData weatherData = new WeatherData(cw.getName(), cw.getMain().getStringTemp(), cw.getWind().getSpeed(), cw.getMain().getHumidity(), cw.getMain().getPressure(), cw.getSys().getSunset(), cw.getSys().getSunrise(), cw.getWeather().get(0).getIcon());
                        DataCache dataCache = new DataCache(lat, lon);
                        dataCache.setWeatherData(weatherData);
                        dataCache.setDate(new Date());

                        try {
                            HelperFactory.getHelper().getDataCashDAO().create(dataCache);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        getViewState().dialogDismiss();
                        getViewState().weatherView(cw);
                    } else {
                        getViewState().error();
                    }
                }

                @Override
                public void onFailure(Call<CurrentWeather> call, Throwable t) {
                    getViewState().error();
                }
            });
    }
}
