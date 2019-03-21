package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.rdb.refresh.Refresh;

public class ExpListContainer extends RefreshContainer<ExpandableListView> {

    private BaseExpandableListAdapter adapter;

    public ExpListContainer(Context context) {
        super(context);
    }

    public ExpListContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpListContainer(Context context, ExpandableListView refreshableView) {
        super(context, refreshableView);
    }

    @Override
    protected ExpandableListView findRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ExpandableListView) {
                return (ExpandableListView) child;
            }
        }
        return null;
    }

    @Override
    protected ExpandableListView createRefreshableView() {
        return new ExpandableListView(getContext());
    }

    @Override
    protected LoadController initLoadController() {
        return Refresh.getExpandableListLoadController();
    }

    @Override
    protected RefreshController initRefreshController() {
        return new ExpListController();
    }

    @Override
    protected void onLoadControllerChanged() {
        updateAdapterInner();
    }

    public void setAdapter(BaseExpandableListAdapter adapter) {
        this.adapter = adapter;
        updateAdapterInner();
    }

    private void updateAdapterInner() {
        if (adapter == null) {
            refreshableView.setAdapter((ExpandableListAdapter) null);
        } else {
            if (loadController == null) {
                throw new RuntimeException("unset LoadController");
            } else {
                ExpListAdapter expListAdapter = new ExpListAdapter(loadController, this, adapter);
                refreshableView.setAdapter(expListAdapter);
            }
        }
    }
}
