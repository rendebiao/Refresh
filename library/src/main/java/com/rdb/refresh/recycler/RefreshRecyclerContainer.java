package com.rdb.refresh.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdb.refresh.RefreshContainer;

public class RefreshRecyclerContainer extends RefreshContainer<RecyclerView> {

    private RecyclerView recyclerView;
    private RefreshRecyclerAdapter refreshRecyclerAdapter;

    public RefreshRecyclerContainer(Context context) {
        super(context);
    }

    public RefreshRecyclerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RecyclerView createRefreshableView(Context context) {
        recyclerView = new RecyclerView(context);
        setRefreshController(new RefreshRecyclerController(recyclerView));
        return recyclerView;
    }

    public void setLayoutManager(@Nullable RecyclerView.LayoutManager layout) {
        recyclerView.setLayoutManager(layout);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            recyclerView.setAdapter(null);
        } else {
            refreshRecyclerAdapter = new RefreshRecyclerAdapter(adapter) {
                @Override
                protected RefreshItemHolder onCreateLoadHolder(ViewGroup viewGroup) {
                    View view = LayoutInflater.from(getContext()).inflate(loadController.getLoadLayout(), viewGroup, false);
                    return new RefreshItemHolder(view, null);
                }

                @Override
                protected void onBindLoadHolder(RefreshItemHolder refreshItemHolder) {
                    loadController.updateLoadView(refreshItemHolder.itemView, isLoading());
                }
            };
            recyclerView.setAdapter(refreshRecyclerAdapter);
        }
    }
}
