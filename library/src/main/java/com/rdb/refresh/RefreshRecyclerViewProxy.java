package com.rdb.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.rdb.refresh.view.RefreshRecyclerViewContainer;

public class RefreshRecyclerViewProxy<D, H extends RecyclerView.ViewHolder> extends RefreshPageProxy<D, RefreshRecyclerViewContainer> {

    protected RecyclerView recyclerView;
    protected RefreshBaseRecyclerAdapter<D> adapter;

    public RefreshRecyclerViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest, RefreshBaseRecyclerAdapter<D> adapter) {
        super(context, refreshListConfig, refreshRequest);
        recyclerView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected RefreshRecyclerViewContainer createRefreshContainer(Context context) {
        return new RefreshRecyclerViewContainer(context);
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.getItemCount() == 0;
    }

    public final RecyclerView getRefreshableView() {
        return recyclerView;
    }

    public final RefreshBaseRecyclerAdapter<D> getAdapter() {
        return adapter;
    }

    @Override
    public void scrollToTop() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public final void scrollToBottom() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(adapter.getItemCount());
        }
    }
}
