package ru.job4j.tourist.di.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import ru.job4j.tourist.di.scope.ViewModelScope;
import ru.job4j.tourist.domain.ApplicationViewModel;
import ru.job4j.tourist.repository.AppRepository;

@Module
public class ViewModelModule {
    private Application application;

    public ViewModelModule(Application application) {
        this.application = application;
    }

    @ViewModelScope
    @Provides
    public ApplicationViewModel providesApplicationViewModel(AppRepository repository) {
        return new ApplicationViewModel(application, repository);
    }
}
