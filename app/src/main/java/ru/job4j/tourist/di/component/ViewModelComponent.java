package ru.job4j.tourist.di.component;

import dagger.Component;
import ru.job4j.tourist.di.module.ViewModelModule;
import ru.job4j.tourist.di.scope.ViewModelScope;
import ru.job4j.tourist.presentation.view.activity.MainActivity;
import ru.job4j.tourist.presentation.view.fragments.MapFragment;
import ru.job4j.tourist.presentation.view.fragments.SavedPinsFragment;

@ViewModelScope
@Component(modules = {ViewModelModule.class}, dependencies = {RepositoryComponent.class})
public interface ViewModelComponent {
    void inject(MainActivity activity);

    void inject(MapFragment fragment);

    void inject(SavedPinsFragment fragment);
}
