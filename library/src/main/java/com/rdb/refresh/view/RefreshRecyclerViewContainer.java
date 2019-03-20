package com.rdb.refresh.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.rdb.refresh.Refresh;

public class RefreshRecyclerViewContainer extends RefreshContainer<RecyclerView> {

    private RefreshRecyclerViewAdapter refreshRecyclerViewAdapter;

    public RefreshRecyclerViewContainer(Context context) {
        super(context);
        setRefreshLoadController(Refresh.getRecyclerLoadController());
    }

    public RefreshRecyclerViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshLoadController(Refresh.getRecyclerLoadController());
    }

    public RefreshRecyclerViewContainer(Context context, RecyclerView refreshableView) {
        super(context, refreshableView);
        setRefreshLoadController(Refresh.getRecyclerLoadController());
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
    public void setRefreshableView(RecyclerView view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshRecyclerViewController(view));
    }

    public void setLayoutManager(@Nullable RecyclerView.LayoutManager layout) {
        refreshableView.setLayoutManager(layout);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            refreshableView.setAdapter(null);
        } else {
            if (loadController == null) {
                throw new RuntimeException("unset RefreshLoadController");
            } else {
                refreshRecyclerViewAdapter = new RefreshRecyclerViewAdapter(loadController, this, adapter);
                refreshableView.setAdapter(refreshRecyclerViewAdapter);
            }
        }
    }
}
