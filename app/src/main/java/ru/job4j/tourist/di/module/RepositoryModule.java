package ru.job4j.tourist.di.module;

import dagger.Module;
import dagger.Provides;
import ru.job4j.tourist.di.scope.RepositoryScope;
import ru.job4j.tourist.repository.AppRepository;
import ru.job4j.tourist.repository.database.dao.PinDao;

@Module
public class RepositoryModule {
    @RepositoryScope
    @Provides
    public AppRepository providesAppRepository(PinDao pinDao) {
        return new AppRepository(pinDao);
    }
}
