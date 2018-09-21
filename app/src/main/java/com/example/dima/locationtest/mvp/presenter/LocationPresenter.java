package com.example.dima.locationtest.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.locationtest.mvp.view.SetUpLocationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.regex.Pattern;

@InjectViewState
public class LocationPresenter extends MvpPresenter<SetUpLocationView> {
    private static final int UPDATE_INTERVAL = 5000;
    private static final int FATEST_INTERVAL = 3000;
    private static final int DISPLACEMENT = 10;

    private LocationCallback locationCallback;
    private Location lastLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    public void setFusedLocationProvider(Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        getViewState().setUpLocation();
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FATEST_INTERVAL);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                lastLocation = locationResult.getLocations().get(locationResult.getLocations().size() - 1); //get last location
                onDisplayLastLocation();
            }
        };

    }
    public void buildLocation()
    {
        buildLocationCallback();
        createLocationRequest();
        onDisplayLastLocation();
    }

    @SuppressLint("MissingPermission")
    public void onDisplayLastLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                lastLocation = location;
                closeLocation();
            }
        });
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        if (lastLocation != null) {
            getViewState().getLocation(lastLocation);
            closeLocation();
        }

    }

    public void closeLocation() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
