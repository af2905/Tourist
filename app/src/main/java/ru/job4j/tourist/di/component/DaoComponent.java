package ru.job4j.tourist.di.component;

import dagger.Component;
import ru.job4j.tourist.di.module.DaoModule;
import ru.job4j.tourist.repository.database.dao.PinDao;

@Component(modules = {DaoModule.class})
public interface DaoComponent {
    PinDao getPinDao();
}
