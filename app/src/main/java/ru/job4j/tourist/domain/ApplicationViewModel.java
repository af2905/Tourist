package ru.job4j.tourist.domain;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.job4j.tourist.repository.AppRepository;
import ru.job4j.tourist.repository.database.entity.PinEntity;

public class ApplicationViewModel extends AndroidViewModel {
    private AppRepository repository;
    private Disposable disposable;
    private MutableLiveData<GoogleMap> map = new MutableLiveData<>();
    private MutableLiveData<List<PinEntity>> liveDataPins = new MutableLiveData<>();
    private MutableLiveData<PinEntity> liveDataSelectedPin = new MutableLiveData<>();
    private MutableLiveData<Integer> liveDataSelectedPinId = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPinSelected = new MutableLiveData<>();

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
        disposable = repository.getAllPins().subscribe(list -> liveDataPins.setValue(list));
    }

    @SuppressLint("CheckResult")
    public void getSelectedPin() {
        if (getLiveDataSelectedPinId().getValue() != null) {
            int id = getLiveDataSelectedPinId().getValue();
            disposable = repository.getPinById(id).subscribe(pin -> liveDataSelectedPin.setValue(pin));
        }
    }

    public LiveData<List<PinEntity>> getLiveDataPins() {
        return liveDataPins;
    }

    public LiveData<GoogleMap> getMap() {
        return map;
    }

    public void setMap(MutableLiveData<GoogleMap> map) {
        this.map = map;
    }

    public LiveData<PinEntity> getLiveDataSelectedPin() {
        return liveDataSelectedPin;
    }

    public LiveData<Integer> getLiveDataSelectedPinId() {
        return liveDataSelectedPinId;
    }

    public void setLiveDataSelectedPinId(int id) {
        MutableLiveData<Integer> pinId = new MutableLiveData<>();
        pinId.setValue(id);
        this.liveDataSelectedPinId = pinId;
    }

    public LiveData<Boolean> getIsPinSelected() {
        return isPinSelected;
    }

    public void setIsPinSelected(Boolean isSelected) {
        MutableLiveData<Boolean> temp = new MutableLiveData<>();
        temp.setValue(isSelected);
        this.isPinSelected = temp;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
