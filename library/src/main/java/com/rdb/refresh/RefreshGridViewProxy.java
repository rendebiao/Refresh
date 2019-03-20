package com.rdb.refresh;

import android.content.Context;
import android.widget.GridView;

import com.rdb.refresh.view.RefreshGridViewContainer;

import java.util.List;

public class RefreshGridViewProxy<D> extends RefreshPageProxy<D, RefreshGridViewContainer> {

    protected GridView gridView;
    protected BaseAdapter adapter;

    public RefreshGridViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest, BaseAdapter<D> adapter) {
        super(context, refreshListConfig, refreshRequest);
        gridView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected RefreshGridViewContainer createRefreshContainer(Context context) {
        return new RefreshGridViewContainer(context);
    }

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.isEmpty();
    }

    public GridView getGridView() {
        return gridView;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public final void scrollToTop() {
        if (gridView != null) {
            if (gridView.getFirstVisiblePosition() > 10 * gridView.getNumColumns()) {
                gridView.setSelection(10 * gridView.getNumColumns());
            }
            gridView.smoothScrollToPosition(0);
        }
    }

    @Override
    public final void scrollToBottom() {
        if (gridView != null) {
            gridView.setSelection(Integer.MAX_VALUE);
        }
    }

    public static abstract class BaseAdapter<D> extends android.widget.BaseAdapter {

        private List<D> items;

        public BaseAdapter() {
        }

        private void setItems(List<D> items) {
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
    }
}
