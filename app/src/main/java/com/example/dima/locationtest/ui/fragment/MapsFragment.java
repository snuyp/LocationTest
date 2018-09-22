package com.example.dima.locationtest.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.dima.locationtest.Common;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.dao.HelperFactory;
import com.example.dima.locationtest.mvp.model.weather.db.DataCache;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;
import java.util.List;

public class MapsFragment extends MvpAppCompatFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static MapsFragment sGoogleMapsFragment = null;
    private MapView mMapView;

    public static MapsFragment getInstance() {

        if (sGoogleMapsFragment == null) {
            sGoogleMapsFragment = new MapsFragment();
        }
        return sGoogleMapsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocation();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
            mMapView.onStop();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getActivity());
        mMap = googleMap;
        setMyLocation(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().isMapToolbarEnabled();

        // Add a marker in Sydney and move the camera
        LatLng minsk = new LatLng(53.9008552, 27.5413905);
        mMap.addMarker(new MarkerOptions().position(minsk).title("Hello Minsk!"));

        CameraPosition cameraPosition = CameraPosition.builder().
                target(minsk).zoom(10).bearing(0).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        addMarker();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }

    private void addMarker()
    {
        try {
          List<DataCache> dataCaches =  HelperFactory.getHelper().getDataCashDAO().getAll();

          for(DataCache cache : dataCaches)
          {
              LatLng latLng = new LatLng(cache.getLatitude(),cache.getLongitude());
              mMap.addMarker(new MarkerOptions().position(latLng).title(cache.getDate().toString())).isVisible();
          }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, Common.PERMISSIONS_REQUEST_CODE);
        } else {
            //if not permissions
        }
    }
    public void setMyLocation(boolean isLocation) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(isLocation);
        }
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
}
