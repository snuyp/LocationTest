package com.example.dima.locationtest.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.db.DataCash;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherActivity extends MvpAppCompatActivity{
    DataCash dataCash;
    private TextView cityTemp, sun, windSpeedHumidity, pressure,date;
    private ImageView weatherImage;
    private TextView coordTextView;
    private SimpleDateFormat sdf = new SimpleDateFormat("E dd.MM.yyyy '[' HH:mm:ss ']'" ,new Locale("en","US"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //Weather
        cityTemp = findViewById(R.id.weather_name_t);
        windSpeedHumidity = findViewById(R.id.weather_wind_speed);
        sun = findViewById(R.id.weather_sunrise);
        pressure = findViewById(R.id.weather_pressure);
        weatherImage = findViewById(R.id.weather_image);
        date = findViewById(R.id.date);
        coordTextView = findViewById(R.id.coords);


        if (getIntent().getExtras().get("weather") != null) {
            dataCash = (DataCash) getIntent().getExtras().get("weather");

            String coords =getResources().getString(R.string.your_coordinates)+" "+ dataCash.getLatitude()+" / "+dataCash.getLongitude();
            coordTextView.setText(coords);

            String cityT = dataCash.getWeatherData().getCity() + " " + dataCash.getWeatherData().getTemp();
            cityTemp.setText(cityT);
            pressure.setText(
                    String.format("%s %s", getResources().getString(R.string.pressure),
                            dataCash.getWeatherData().getPressure()));

            windSpeedHumidity.setText(
                    String.format("%s %s\n%s %s", getResources().getString(R.string.wind_speed),
                            dataCash.getWeatherData().getWindSpeed(), getResources().getString(R.string.humidity),
                            dataCash.getWeatherData().getHumidity()));

            sun.setText(String.format("%s%s", dataCash.getWeatherData().getSunrise(), dataCash.getWeatherData().getSunset()));

            String d = sdf.format(dataCash.getDate());
            date.setText(d);


            Glide.with(this)
                    .load(dataCash.getWeatherData().getIcon())
                    .into(weatherImage);
        }
    }
}
