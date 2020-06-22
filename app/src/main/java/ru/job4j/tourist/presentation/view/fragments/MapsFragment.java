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
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import ru.job4j.tourist.R;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private Location location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        initMaps();
        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this::saveLocation);

        return view;
    }

    private void initMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps_fragment);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Objects.requireNonNull(locationManager).requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 0, getChangedLocation());
    }

    public void saveLocation(View view) {
        if (location != null) {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(currentLocation).title("Hello Maps");
            marker.flat(true);
            map.addMarker(marker);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        }
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
}