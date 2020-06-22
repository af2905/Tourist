package ru.job4j.tourist.presentation.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;

import ru.job4j.tourist.R;
import ru.job4j.tourist.presentation.view.fragments.MapsFragment;

public class MainActivity extends AppCompatActivity {
    private static final String MAPS_FRAGMENT = "mapsFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Places.initialize(this, getString(R.string.google_maps_key));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new MapsFragment(), MAPS_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }
}