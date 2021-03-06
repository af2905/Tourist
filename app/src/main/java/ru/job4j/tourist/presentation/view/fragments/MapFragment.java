package ru.job4j.tourist.presentation.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import ru.job4j.tourist.R;
import ru.job4j.tourist.di.component.ViewModelComponent;
import ru.job4j.tourist.domain.ApplicationViewModel;
import ru.job4j.tourist.presentation.base.BaseFragment;
import ru.job4j.tourist.repository.database.entity.PinEntity;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends BaseFragment
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private static final int REQUEST_CODE = 123;
    private GoogleMap map;
    private Location location;
    private LocationManager locationManager;

    @Inject
    protected ApplicationViewModel applicationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(LOCATION_SERVICE);
        initMap();
        applicationViewModel.getAllPins();
        applicationViewModel.getMap().observe(this, googleMap -> map = googleMap);
        applicationViewModel.getLiveDataPins().observe(this, this::loadSavedPins);

        Boolean isPinSelected = applicationViewModel.getIsPinSelected().getValue();
        if (isPinSelected != null && isPinSelected) {
            showSelectedSavedPin();
        }
        return view;
    }

    @Override
    protected void injectDependency(ViewModelComponent component) {
        component.inject(this);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MutableLiveData<GoogleMap> currentMap = new MutableLiveData<>();
        currentMap.setValue(map);
        applicationViewModel.setMap(currentMap);
        setCustomUiSettings(map);
        checkPermissions(map);
        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
    }

    private void setCustomUiSettings(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
    }

    private void checkPermissions(GoogleMap map) {
        int fineLocationPermissionStatus = ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationPermissionStatus = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLocationPermissionStatus == PackageManager.PERMISSION_GRANTED
                && coarseLocationPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            Objects.requireNonNull(locationManager).requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 0, getChangedLocation());
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length == 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                return;
            }
            return;
        }
        throw new IllegalStateException("Unexpected value: " + requestCode);
    }

    private LocationListener getChangedLocation() {
        LocationListener locationListener;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location lct) {
                location = lct;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        return locationListener;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        PinEntity pin = new PinEntity(
                String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), getString(R.string.landmark));
        applicationViewModel.insertPin(pin);
        applicationViewModel.getAllPins();
        applicationViewModel.setCount(1);

        BottomNavigationView navigationView;
        navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_navigation_view);
        BadgeDrawable badgeDrawable = navigationView.getOrCreateBadge(R.id.bottomNavigationSavedMenuId);
        applicationViewModel.getCount().observe(this, badgeDrawable::setNumber);
    }

    private void loadSavedPins(List<PinEntity> pins) {
        for (PinEntity pin : pins) {
            MarkerOptions marker =
                    new MarkerOptions().position(getPinLatLng(pin)).title(pin.getTitle());
            marker.flat(true);
            map.addMarker(marker);
        }
    }

    private void showSelectedSavedPin() {
        applicationViewModel.getSelectedPin();
        applicationViewModel.getLiveDataSelectedPin().observe(this, pin -> {
            MarkerOptions marker =
                    new MarkerOptions().position(getPinLatLng(pin)).title(getString(R.string.landmark));
            marker.flat(true);
            map.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(getPinLatLng(pin))
                    .zoom(15)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            map.animateCamera(cameraUpdate);
        });
    }

    private LatLng getPinLatLng(PinEntity pin) {
        double lat = Double.parseDouble(pin.getLatitude());
        double lng = Double.parseDouble(pin.getLongitude());
        return new LatLng(lat, lng);
    }
}