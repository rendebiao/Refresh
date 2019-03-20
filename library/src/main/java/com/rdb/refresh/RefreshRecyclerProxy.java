package com.rdb.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdb.refresh.recycler.RefreshRecyclerViewContainer;

import java.util.ArrayList;

public abstract class RefreshRecyclerProxy<D, H extends RecyclerView.ViewHolder> extends RefreshPageProxy<D, RefreshRecyclerViewContainer> {

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter<H> adapter;

    public RefreshRecyclerProxy(Context context) {
        super(context);
    }

    @Override
    public void onCreateView(View view) {
        super.onCreateView(view);
        recyclerView = refreshContainer.getRefreshableView();
        adapter = createAdapter(context, getDataList());
        recyclerView.setAdapter(adapter);
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
}
