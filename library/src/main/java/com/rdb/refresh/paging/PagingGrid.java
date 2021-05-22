package com.rdb.refresh.paging;

import android.content.Context;
import android.widget.GridView;

import com.rdb.refresh.view.GridContainer;

public class PagingGrid<D> extends Paging<D, GridContainer> {

    protected GridView gridView;
    protected BaseAdapter<D> adapter;

    public PagingGrid(Context context, Config config, Request request, BaseAdapter<D> adapter) {
        super(context, config, request);
        gridView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected GridContainer createRefreshContainer(Context context) {
        return new GridContainer(context);
    }

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter != null && adapter.isEmpty();
    }

    public GridView getRefreshableView() {
        return gridView;
    }

    public BaseAdapter<D> getAdapter() {
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
