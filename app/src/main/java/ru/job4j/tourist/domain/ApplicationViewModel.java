package ru.job4j.tourist.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ru.job4j.tourist.repository.AppRepository;

public class ApplicationViewModel extends AndroidViewModel {
    private AppRepository repository;

    public ApplicationViewModel(@NonNull Application application, AppRepository repository) {
        super(application);
        this.repository = repository;
    }
}
