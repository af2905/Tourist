package ru.job4j.tourist.presentation.item;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.job4j.tourist.R;
import ru.job4j.tourist.repository.database.entity.PinEntity;

public class PinViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private PinEntity pin;
    private IPinClickListener<PinEntity> clickListener;
    private View.OnClickListener openPin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickListener.showPin(pin);
        }
    };

    public PinViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(PinEntity pin, IPinClickListener<PinEntity> clickListener) {
        this.pin = pin;
        this.clickListener = clickListener;
        setupItem();
    }

    private void setupItem() {
        TextView locationInfo = itemView.findViewById(R.id.location_info);
        locationInfo.setText(String.format("%s, %s, %s", pin.getLatitude(), pin.getLongitude(), pin.getTitle()));
        itemView.setOnClickListener(openPin);
    }
}
