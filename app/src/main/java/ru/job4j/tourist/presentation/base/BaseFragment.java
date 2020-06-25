package ru.job4j.tourist.presentation.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.job4j.tourist.App;
import ru.job4j.tourist.di.component.ViewModelComponent;

public abstract class BaseFragment extends Fragment {
    private AppCompatActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDaggerDependencies();
    }

    private void createDaggerDependencies() {
        injectDependency(((App) (activity.getApplication())).getViewModelComponent());
    }

    protected abstract void injectDependency(ViewModelComponent component);

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (AppCompatActivity) context;
        super.onAttach(context);
    }
}



/*
    private AppCompatActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDaggerDependencies();
    }

    private void createDaggerDependencies() {
        injectDependency(((App) (activity).getApplication()).getViewModelComponent());
    }

    protected abstract void injectDependency(ViewModelComponent component);

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (AppCompatActivity) context;
        super.onAttach(context);
    }*/
