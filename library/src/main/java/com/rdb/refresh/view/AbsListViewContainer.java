package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

public abstract class AbsListViewContainer<T extends AbsListView> extends Container<T> {

    private BaseAdapter adapter;

    public AbsListViewContainer(Context context) {
        super(context);
    }

    public AbsListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsListViewContainer(Context context, T refreshableView) {
        super(context, refreshableView);
    }

    @Override
    protected RefreshController initRefreshController() {
        return new AbsListViewController();
    }

    @Override
    protected void onLoadControllerChanged() {
        updateAdapterInner();
    }

    public void setAdapter(BaseAdapter adapter) {
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
                AbsListViewWrapperAdapter absListViewWrapperAdapter = new AbsListViewWrapperAdapter(loadController, this, adapter);
                refreshableView.setAdapter(absListViewWrapperAdapter);
            }
        }
    }
}