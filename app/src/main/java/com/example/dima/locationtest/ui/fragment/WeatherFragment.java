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
import com.example.dima.locationtest.mvp.presenter.WeatherPresenter;
import com.example.dima.locationtest.mvp.view.WeatherView;

import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherView {

    @InjectPresenter
    WeatherPresenter weatherPresenter;

    private TextView cityTemp, sun, windSpeedHumidity, pressure;
    private ImageView weatherImage;
    private TextView locationTextView;
    private SpotsDialog dialog;
    private String cords;
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
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        setUpLocation();
        locationTextView = v.findViewById(R.id.coords);

        //Weather
        cityTemp = v.findViewById(R.id.weather_name_t);
        windSpeedHumidity = v.findViewById(R.id.weather_wind_speed);
        sun = v.findViewById(R.id.weather_sunrise);
        pressure = v.findViewById(R.id.weather_pressure);
        weatherImage = v.findViewById(R.id.weather_image);
        dialog = new SpotsDialog(getContext());

        setCoordinates();

        return v;
    }
    public void setCoordinates()
    {
        if (Common.lat == 0 && Common.lon == 0) {
            SmartLocation.with(getActivity()).location()
                    .oneFix()
                    .start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            Common.lat = location.getLatitude();
                            Common.lon = location.getLongitude();
                            weatherPresenter.loadWeather(location.getLatitude(),location.getLongitude());

                            cords = String.valueOf(getResources().getString(R.string.your_coordinates)
                                    + " " + Common.lat
                                    + " / " + Common.lon);
                            locationTextView.setText(cords);
                        }
                    });
        }
        else
        {
            weatherPresenter.loadWeather(Common.lat,Common.lon);
            locationTextView.setText(cords);
        }
    }

    //for example Reverse geocoding
//        SmartLocation.with(context).geocoding()
//                .reverse(location, new OnReverseGeocodingListener() {
//                    @Override
//                    public onAddressResolved(Location original, List<Address> results) {
//                        // ...
//                    }
//                });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
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
        dialog.show();
    }

    @Override
    public void dialogDismiss() {
        dialog.dismiss();
    }

    @Override
    public void error() {
        Toasty.error(getContext(), "Error").show();
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

        }

    }

    public void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, Common.PERMISSIONS_REQUEST_CODE);

        } else {

        }
    }

}
