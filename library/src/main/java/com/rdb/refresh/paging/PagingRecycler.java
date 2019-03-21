package com.rdb.refresh.paging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.rdb.refresh.view.RecyclerContainer;

public class PagingRecycler<D> extends Paging<D, RecyclerContainer> {

    protected RecyclerView recyclerView;
    protected BaseRecyclerAdapter<D> adapter;

    public PagingRecycler(Context context, Config config, Request request, BaseRecyclerAdapter<D> adapter) {
        super(context, config, request);
        recyclerView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected RecyclerContainer createRefreshContainer(Context context) {
        return new RecyclerContainer(context);
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

    public final BaseRecyclerAdapter<D> getAdapter() {
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
