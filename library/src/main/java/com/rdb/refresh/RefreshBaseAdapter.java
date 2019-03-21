package com.rdb.refresh;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class RefreshBaseAdapter<D> extends android.widget.BaseAdapter {

    private List<D> items;

    public RefreshBaseAdapter() {
    }

    void setItems(List<D> items) {
        this.items = items;
    }

    @Override
    public final int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public final D getItem(int position) {
        return items.get(position);
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (convertView == null) {
            convertView = onCreateItemView(parent, viewType);
        }
        onUpdateItemView(convertView, position);
        return convertView;
    }

    public abstract View onCreateItemView(ViewGroup parent, int viewType);

    public abstract void onUpdateItemView(View convertView, int position);
}
