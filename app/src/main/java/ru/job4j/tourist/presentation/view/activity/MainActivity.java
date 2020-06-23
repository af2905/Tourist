package ru.job4j.tourist.presentation.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String MAPS_FRAGMENT = "mapsFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Places.initialize(this, getString(R.string.google_maps_key));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
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
                Toast.makeText(this, "Explore", Toast.LENGTH_SHORT).show();
                loadMapsFragment();
                return true;
            case R.id.bottomNavigationSavedMenuId:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                return true;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }

    private void loadMapsFragment() {
        loadFragment(new MapsFragment(), MAPS_FRAGMENT);
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