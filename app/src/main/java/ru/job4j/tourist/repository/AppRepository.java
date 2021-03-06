package ru.job4j.tourist.repository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.job4j.tourist.repository.database.dao.PinDao;
import ru.job4j.tourist.repository.database.entity.PinEntity;

public class AppRepository {
    private PinDao pinDao;

    public AppRepository(PinDao pinDao) {
        this.pinDao = pinDao;
    }

    public void insertPin(PinEntity pin) {
        pinDao.insert(pin);
    }

    public void deletePin(PinEntity pin) {
        pinDao.delete(pin);
    }

    public Single<List<PinEntity>> getAllPins() {
        return Single.just(pinDao.getAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<PinEntity> getPinById(int id) {
        return Single.just(pinDao.getById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}