package com.rdb.refresh.abslist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.rdb.refresh.RefreshContainer;

public abstract class RefreshAbsListViewContainer<T extends AbsListView> extends RefreshContainer<T> {

    private RefreshAbsListViewAdapter refreshAbsListViewAdapter;

    public RefreshAbsListViewContainer(Context context) {
        super(context);
    }

    public RefreshAbsListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRefreshableView(T view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshAbsListViewController(view));
    }

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            refreshableView.setAdapter(null);
        } else {
            if (loadController == null) {
                throw new RuntimeException("unset RefreshLoadController");
            } else {
                refreshAbsListViewAdapter = new RefreshAbsListViewAdapter(loadController, this, adapter);
                refreshableView.setAdapter(refreshAbsListViewAdapter);
            }
        }
    }
}