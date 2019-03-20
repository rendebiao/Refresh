package com.rdb.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdb.refresh.view.RefreshRecyclerViewContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class RefreshRecyclerViewProxy<D, H extends RecyclerView.ViewHolder> extends RefreshPageProxy<D, RefreshRecyclerViewContainer> {

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter<H> adapter;

    public RefreshRecyclerViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest) {
        super(context, refreshListConfig, refreshRequest);
        recyclerView = refreshContainer.getRefreshableView();
        adapter = createAdapter(context, getDataList());
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected RefreshRecyclerViewContainer createRefreshContainer(Context context) {
        return new RefreshRecyclerViewContainer(context);
    }

    protected abstract RecyclerView.Adapter<H> createAdapter(Context context, ArrayList<D> items);

    @Override
    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.getItemCount() == 0;
    }

    public final RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public final RecyclerView.Adapter<H> getAdapter() {
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

    public static abstract class BaseAdapter<D> extends RecyclerView.Adapter<ViewHolder> {

        private List<D> items;

        public BaseAdapter() {
        }

        private void setItems(List<D> items) {
            this.items = items;
        }

        @Override
        public int getItemCount() {
            return items == null ? 0 : items.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
