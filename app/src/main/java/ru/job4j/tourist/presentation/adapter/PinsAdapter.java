package ru.job4j.tourist.presentation.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.job4j.tourist.presentation.item.PinViewHolder;
import ru.job4j.tourist.repository.database.entity.PinEntity;

public class PinsAdapter extends RecyclerView.Adapter<PinViewHolder> {
    private List<PinEntity> pins;

    public PinsAdapter(List<PinEntity> pins) {
        this.pins = pins;
    }

    @NonNull
    @Override
    public PinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PinViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
