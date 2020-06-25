package ru.job4j.tourist.presentation.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.job4j.tourist.R;
import ru.job4j.tourist.presentation.view.fragments.MapsFragment;
import ru.job4j.tourist.presentation.view.fragments.SavedPinsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String MAPS_FRAGMENT = "mapsFragment";
    private static final String SAVED_PINS_FRAGMENT = "savedPinsFragment";
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Places.initialize(this, getString(R.string.google_maps_key));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.inflateMenu(R.menu.bottom_navigation_menu);
        navigationView.setOnNavigationItemSelectedListener(this);
        BadgeDrawable badgeDrawable = navigationView.getOrCreateBadge(R.id.bottomNavigationSavedMenuId);
        badgeDrawable.setNumber(1);

        if (savedInstanceState == null) {
            loadMapsFragment();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottomNavigationExploreMenuId:
                loadMapsFragment();
                return true;
            case R.id.bottomNavigationSavedMenuId:
                loadSavedPinsFragment();
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }

    private void loadMapsFragment() {
        loadFragment(new MapsFragment(), MAPS_FRAGMENT);
    }

    private void loadSavedPinsFragment() {
        loadFragment(new SavedPinsFragment(), SAVED_PINS_FRAGMENT);
    }

    private void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}