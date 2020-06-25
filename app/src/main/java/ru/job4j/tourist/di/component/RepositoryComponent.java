package ru.job4j.tourist.di.component;

import dagger.Component;
import ru.job4j.tourist.di.module.RepositoryModule;
import ru.job4j.tourist.di.scope.RepositoryScope;
import ru.job4j.tourist.repository.AppRepository;

@RepositoryScope
@Component(modules = {RepositoryModule.class}, dependencies = {DaoComponent.class})
public interface RepositoryComponent {
    AppRepository getRepository();
}
