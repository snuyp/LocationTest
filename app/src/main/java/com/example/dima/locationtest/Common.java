package com.example.dima.locationtest;

import com.example.dima.locationtest.remote.WeatherClient;
import com.example.dima.locationtest.remote.WeatherService;

public class Common {
    public static final int PERMISSIONS_REQUEST_CODE = 100;

    private static final String BASE_WEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String WEATHER_API_KEY = "414028c19422cc451a324493b5df909b";
    public static String units = "metric";
    public static WeatherService getWeatherService()
    {
        return WeatherClient.getClient(BASE_WEATHER_URL).create(WeatherService.class);
    }
}
