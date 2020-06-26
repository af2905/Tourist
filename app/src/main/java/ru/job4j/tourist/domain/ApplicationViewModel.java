package ru.job4j.tourist.domain;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import ru.job4j.tourist.repository.AppRepository;
import ru.job4j.tourist.repository.database.entity.PinEntity;

public class ApplicationViewModel extends AndroidViewModel {
    private AppRepository repository;
    private MutableLiveData<List<PinEntity>> liveDataPins = new MutableLiveData<>();
    private MutableLiveData<PinEntity> liveDataSelectedPin = new MutableLiveData<>();
    private MutableLiveData<GoogleMap> map = new MutableLiveData<>();

    public ApplicationViewModel(@NonNull Application application, AppRepository repository) {
        super(application);
        this.repository = repository;
    }

    public void insertPin(PinEntity pin) {
        repository.insertPin(pin);
    }

    public void deletePin(PinEntity pin) {
        repository.deletePin(pin);
    }

    @SuppressLint("CheckResult")
    public void getAllPins() {
        repository.getAllPins().subscribe(list -> liveDataPins.setValue(list));
    }

    @SuppressLint("CheckResult")
    public void getPinById(int id) {
        repository.getPinById(id).subscribe(pin -> liveDataSelectedPin.setValue(pin));
    }

    public LiveData<List<PinEntity>> getLiveDataPins() {
        return liveDataPins;
    }

    public MutableLiveData<GoogleMap> getMap() {
        return map;
    }

    public void setMap(MutableLiveData<GoogleMap> map) {
        this.map = map;
    }
}
