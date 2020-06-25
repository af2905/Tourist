package ru.job4j.tourist;

import android.app.Application;

import androidx.room.Room;

import ru.job4j.tourist.di.component.DaggerDaoComponent;
import ru.job4j.tourist.di.component.DaggerRepositoryComponent;
import ru.job4j.tourist.di.component.DaggerViewModelComponent;
import ru.job4j.tourist.di.component.DaoComponent;
import ru.job4j.tourist.di.component.RepositoryComponent;
import ru.job4j.tourist.di.component.ViewModelComponent;
import ru.job4j.tourist.di.module.DaoModule;
import ru.job4j.tourist.di.module.RepositoryModule;
import ru.job4j.tourist.di.module.ViewModelModule;
import ru.job4j.tourist.repository.database.AppDatabase;

public class App extends Application {
    private ViewModelComponent viewModelComponent;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        initRoom();
        initDagger();
    }

    public ViewModelComponent getViewModelComponent() {
        return viewModelComponent;
    }

    private void initRoom() {
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    private void initDagger() {
        DaoComponent daoComponent = DaggerDaoComponent.builder()
                .daoModule(new DaoModule(database))
                .build();

        RepositoryComponent repositoryComponent = DaggerRepositoryComponent.builder()
                .daoComponent(daoComponent)
                .repositoryModule(new RepositoryModule())
                .build();

        viewModelComponent = DaggerViewModelComponent.builder()
                .repositoryComponent(repositoryComponent)
                .viewModelModule(new ViewModelModule(this))
                .build();
    }
}