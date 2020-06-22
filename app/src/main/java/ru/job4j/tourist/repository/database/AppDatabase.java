package ru.job4j.tourist.repository.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.job4j.tourist.repository.database.dao.PinDao;
import ru.job4j.tourist.repository.database.entity.PinEntity;

@Database(entities = {PinEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PinDao pinDao();
}