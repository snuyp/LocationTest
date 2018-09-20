package com.example.dima.locationtest.Interface;

import com.example.dima.locationtest.mvp.model.weather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather?")
    Call<CurrentWeather> getToday(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("units") String units,
            @Query("appid") String appid
    );

//    @GET("weather?")
//    Call<CurrentWeather> getToday(
//            @Query("q") String city,
//            @Query("units") String units,
//            @Query("appid") String appid
//    );

//    @GET("forecast?")
//    Call<ForecastWeather> getForecast(
//            @Query("q") String q,
//            @Query("units") String units,
//            @Query("appid") String appid
//    );
}
