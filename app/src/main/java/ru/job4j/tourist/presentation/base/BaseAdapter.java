package ru.job4j.tourist.presentation.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, M, L> extends RecyclerView.Adapter<VH> {
    protected List<M> list;
    protected L itemClickListener;

    public BaseAdapter(List<M> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemView.setTag(list.get(position));
    }

    public M getItem(int position) {
        return list.get(position);
    }

    public void add(M item) {
        list.add(item);
    }

    public void addAll(List<M> list) {
        for (M item : list) {
            add(item);
        }
    }

    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(L itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
