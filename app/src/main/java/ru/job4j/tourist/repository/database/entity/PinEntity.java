package ru.job4j.tourist.repository.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "pins")
public class PinEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String latitude;
    private String longitude;
    private String title;

    @Ignore
    public PinEntity(int id, String latitude, String longitude, String title) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }

    public PinEntity(String latitude, String longitude, String title) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PinEntity pinEntity = (PinEntity) o;
        return id == pinEntity.id
                && Objects.equals(latitude, pinEntity.latitude)
                && Objects.equals(longitude, pinEntity.longitude)
                && Objects.equals(title, pinEntity.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, title);
    }
}

