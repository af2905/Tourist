package ru.job4j.tourist.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import ru.job4j.tourist.R;
import ru.job4j.tourist.di.component.ViewModelComponent;
import ru.job4j.tourist.domain.ApplicationViewModel;
import ru.job4j.tourist.presentation.adapter.PinsAdapter;
import ru.job4j.tourist.presentation.base.BaseFragment;
import ru.job4j.tourist.presentation.item.DividerItemDecoration;
import ru.job4j.tourist.presentation.item.IPinClickListener;
import ru.job4j.tourist.repository.database.entity.PinEntity;

public class SavedPinsFragment extends BaseFragment {
    private RecyclerView recycler;
    private LinearLayoutManager manager;
    protected CallbackPinClick callback;
    private IPinClickListener<PinEntity> clickListener = pin -> showPin(pin.getId());

    @Inject
    protected ApplicationViewModel applicationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_row, container, false);
        recycler = view.findViewById(R.id.pins_row);
        manager = new LinearLayoutManager(view.getContext());
        applicationViewModel.getLiveDataPins().observe(this, this::initRecyclerView);
        return view;
    }

    @Override
    protected void injectDependency(ViewModelComponent component) {
        component.inject(this);
    }

    private void initRecyclerView(List<PinEntity> pins) {
        PinsAdapter pinsAdapter = new PinsAdapter(pins);
        pinsAdapter.setItemClickListener(clickListener);
        RecyclerView.ItemDecoration decoration
                = new DividerItemDecoration(8, 8, 16, 16);
        recycler.addItemDecoration(decoration);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(pinsAdapter);
    }

    private void showPin(int id) {
        callback.showPin(id);
    }

    public interface CallbackPinClick {
        void showPin(int id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.callback = (CallbackPinClick) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }
}
