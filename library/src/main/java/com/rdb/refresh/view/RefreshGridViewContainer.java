package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.rdb.refresh.Refresh;

public class RefreshGridViewContainer extends RefreshAbsListViewContainer<GridView> {

    public RefreshGridViewContainer(Context context) {
        super(context);
        setRefreshLoadController(Refresh.getGridLoadController());
    }

    public RefreshGridViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshLoadController(Refresh.getGridLoadController());
    }

    public RefreshGridViewContainer(Context context, GridView refreshableView) {
        super(context, refreshableView);
        setRefreshLoadController(Refresh.getGridLoadController());
    }

    @Override
    protected GridView findRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof GridView) {
                return (GridView) child;
            }
        }
        return null;
    }

    @Override
    protected GridView createRefreshableView() {
        return new GridView(getContext());
    }
}