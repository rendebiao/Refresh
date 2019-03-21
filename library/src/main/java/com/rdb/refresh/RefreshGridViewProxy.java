package com.rdb.refresh;

import android.content.Context;
import android.widget.GridView;

import com.rdb.refresh.view.RefreshGridViewContainer;

public class RefreshGridViewProxy<D> extends RefreshPageProxy<D, RefreshGridViewContainer> {

    protected GridView gridView;
    protected RefreshBaseAdapter<D> adapter;

    public RefreshGridViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest, RefreshBaseAdapter<D> adapter) {
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

    public GridView getRefreshableView() {
        return gridView;
    }

    public RefreshBaseAdapter<D> getAdapter() {
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
}
