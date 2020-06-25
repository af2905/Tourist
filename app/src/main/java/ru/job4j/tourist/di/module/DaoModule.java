package ru.job4j.tourist.di.module;

import dagger.Module;
import dagger.Provides;
import ru.job4j.tourist.repository.database.AppDatabase;
import ru.job4j.tourist.repository.database.dao.PinDao;

@Module
public class DaoModule {
    private AppDatabase appDatabase;

    public DaoModule(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Provides
    AppDatabase providesRoomDatabase() {
        return appDatabase;
    }

    @Provides
    PinDao providesPinDao(AppDatabase database) {
       return database.pinDao();
    }
}