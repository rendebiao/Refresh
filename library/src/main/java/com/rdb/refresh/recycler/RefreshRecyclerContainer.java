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

    private RefreshRecyclerAdapter refreshRecyclerAdapter;

    public RefreshRecyclerContainer(Context context) {
        super(context);
    }

    public RefreshRecyclerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof RecyclerView) {
                setRefreshableView((RecyclerView) child);
                break;
            }
        }
    }

    @Override
    public void setRefreshableView(RecyclerView view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshRecyclerController(view));
    }

    public void setLayoutManager(@Nullable RecyclerView.LayoutManager layout) {
        refreshableView.setLayoutManager(layout);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            refreshableView.setAdapter(null);
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
            refreshableView.setAdapter(refreshRecyclerAdapter);
        }
    }
}
