package ru.job4j.tourist.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import ru.job4j.tourist.R;
import ru.job4j.tourist.presentation.base.BaseAdapter;
import ru.job4j.tourist.presentation.item.IPinClickListener;
import ru.job4j.tourist.presentation.item.PinViewHolder;
import ru.job4j.tourist.repository.database.entity.PinEntity;

public class PinsAdapter extends BaseAdapter<PinViewHolder, PinEntity, IPinClickListener> {
    private List<PinEntity> pins;

    public PinsAdapter(List<PinEntity> list) {
        super(list);
        this.pins = list;
    }

    @NonNull
    @Override
    public PinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PinViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PinViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(pins.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
