package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.rdb.refresh.Refresh;

public class GridViewContainer extends AbsListViewContainer<GridView> {

    public GridViewContainer(Context context) {
        super(context);
    }

    public GridViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewContainer(Context context, GridView refreshableView) {
        super(context, refreshableView);
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

    @Override
    protected LoadController initLoadController() {
        return Refresh.getGridLoadController();
    }
}