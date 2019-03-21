package com.rdb.refresh.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.rdb.refresh.Refresh;

public class RecyclerContainer extends RefreshContainer<RecyclerView> {

    private RecyclerView.Adapter adapter;

    public RecyclerContainer(Context context) {
        super(context);
    }

    public RecyclerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerContainer(Context context, RecyclerView refreshableView) {
        super(context, refreshableView);
    }

    @Override
    protected RecyclerView findRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof RecyclerView) {
                return (RecyclerView) child;
            }
        }
        return null;
    }

    @Override
    protected RecyclerView createRefreshableView() {
        return new RecyclerView(getContext());
    }

    @Override
    protected LoadController initLoadController() {
        return Refresh.getRecyclerLoadController();
    }

    @Override
    protected RefreshController initRefreshController() {
        return new RecyclerController();
    }

    @Override
    protected void onLoadControllerChanged() {
        updateAdapterInner();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        updateAdapterInner();
    }

    private void updateAdapterInner() {
        if (adapter == null) {
            refreshableView.setAdapter(null);
        } else {
            if (loadController == null) {
                throw new RuntimeException("unset LoadController");
            } else {
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(loadController, this, adapter);
                refreshableView.setAdapter(recyclerAdapter);
            }
        }
    }
}
