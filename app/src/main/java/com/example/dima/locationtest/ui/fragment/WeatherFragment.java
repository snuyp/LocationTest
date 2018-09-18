package com.example.dima.locationtest.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.example.dima.locationtest.Common;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.CurrentWeather;
import com.example.dima.locationtest.mvp.presenter.LocationPresenter;
import com.example.dima.locationtest.mvp.presenter.WeatherPresenter;
import com.example.dima.locationtest.mvp.view.SetUpLocationView;
import com.example.dima.locationtest.mvp.view.WeatherView;

public class WeatherFragment extends MvpAppCompatFragment implements SetUpLocationView,WeatherView {
    @InjectPresenter
    LocationPresenter locationPresenter;
    @InjectPresenter
    WeatherPresenter weatherPresenter;

    private TextView cityTemp, sun, windSpeedHumidity, pressure;
    private ImageView weatherImage;
    private TextView locationTextView;
    public static WeatherFragment fragment;
    public static WeatherFragment getInstance() {
        if (fragment == null) {
            fragment = new WeatherFragment();
        }
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather,container,false);
        locationPresenter.setFusedLocationProvider(getActivity());
        //locationTextView = v.findViewById(R.id.location);

        //Weather
        cityTemp = v.findViewById(R.id.weather_name_t);
        windSpeedHumidity = v.findViewById(R.id.weather_wind_speed);
        sun = v.findViewById(R.id.weather_sunrise);
        pressure = v.findViewById(R.id.weather_pressure);
        weatherImage = v.findViewById(R.id.weather_image);

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    Common.PERMISSIONS_REQUEST_CODE);
        } else {
            locationPresenter.buildLocationCallback();
            locationPresenter.createLocationRequest();
            locationPresenter.onDisplayLastLocation();
        }

    }
    @Override
    public void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, Common.PERMISSIONS_REQUEST_CODE);
        } else {
            locationPresenter.buildLocationCallback();
            locationPresenter.createLocationRequest();
            locationPresenter.onDisplayLastLocation();
        }
    }
    @Override
    public void displayLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else
        {
            locationPresenter.onDisplayLastLocation();
        }
    }

    @Override
    public void getLocation(Location location) {
        String cords = String.valueOf(location.getLongitude()+" "+location.getLatitude());
//        locationTextView.setText(cords);
        weatherPresenter.loadWeather(location.getLatitude(),location.getLongitude());
    }

    @Override
    public void weatherView(CurrentWeather currentWeather) {
        String cityT = currentWeather.getName() + " " + currentWeather.getMain().getStringTemp().toString();
        cityTemp.setText(cityT);
        pressure.setText(
                String.format("%s %s", getResources().getString(R.string.pressure),
                        currentWeather.getMain().getPressure()));

        windSpeedHumidity.setText(
                String.format("%s %s\n%s %s", getResources().getString(R.string.wind_speed),
                        currentWeather.getWind().getSpeed(), getResources().getString(R.string.humidity),
                        currentWeather.getMain().getHumidity()));

        sun.setText(String.format("%s%s", currentWeather.getSys().getSunrise(), currentWeather.getSys().getSunset()));

        Glide.with(this)
                .load(currentWeather.getWeather().get(0).getIcon())
                .into(weatherImage);
    }

    @Override
    public void dialogShow() {

    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void error() {

    }
}