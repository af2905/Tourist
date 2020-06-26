package ru.job4j.tourist.presentation.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import ru.job4j.tourist.R;
import ru.job4j.tourist.di.component.ViewModelComponent;
import ru.job4j.tourist.domain.ApplicationViewModel;
import ru.job4j.tourist.presentation.base.BaseActivity;
import ru.job4j.tourist.presentation.view.fragments.MapFragment;
import ru.job4j.tourist.presentation.view.fragments.SavedPinsFragment;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, SavedPinsFragment.CallbackPinClick {
    private static final String MAPS_FRAGMENT = "mapsFragment";
    private static final String SAVED_PINS_FRAGMENT = "savedPinsFragment";

    @Inject
    ApplicationViewModel applicationViewModel;

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
    protected void injectDependency(ViewModelComponent component) {
        component.inject(this);
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
        loadFragment(new MapFragment(), MAPS_FRAGMENT);
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
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPin(int id) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }
}