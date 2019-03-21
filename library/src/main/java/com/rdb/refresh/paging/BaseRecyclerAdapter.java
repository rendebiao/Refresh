package com.rdb.refresh.paging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<D> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {

    private List<D> items;

    public BaseRecyclerAdapter() {
    }

    void setItems(List<D> items) {
        this.items = items;
    }

    @Override
    public final int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public final D getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(onCreateItemView(viewGroup, viewType));
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        onUpdateItemView(viewHolder.itemView, position);
    }

    public abstract View onCreateItemView(ViewGroup parent, int viewType);

    public abstract void onUpdateItemView(View convertView, int position);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
