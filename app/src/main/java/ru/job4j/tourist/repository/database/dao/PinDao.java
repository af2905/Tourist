package ru.job4j.tourist.repository.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.job4j.tourist.repository.database.entity.PinEntity;

@Dao
public interface PinDao {
    @Query("SELECT * FROM pins")
    List<PinEntity> getAll();

    @Query("SELECT * FROM pins WHERE id =:id")
    PinEntity getById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PinEntity pin);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<PinEntity> pins);

    @Update
    void update(PinEntity pin);

    @Update
    void updateAll(List<PinEntity> pins);

    @Delete
    void delete(PinEntity pin);
}
