package com.rdb.refresh.abslist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.rdb.refresh.RefreshLoadController;

public class RefreshGridViewContainer extends RefreshAbsListViewContainer<GridView> {

    private static RefreshLoadController defaultLoadController;

    public RefreshGridViewContainer(Context context) {
        super(context);
        setRefreshLoadController(defaultLoadController);
    }

    public RefreshGridViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshLoadController(defaultLoadController);
    }

    public static void setDefaultRefreshLoadController(RefreshLoadController defaultLoadController) {
        RefreshGridViewContainer.defaultLoadController = defaultLoadController;
    }

    @Override
    protected void findAndInitRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof GridView) {
                setRefreshableView((GridView) child);
                return;
            }
        }
        setRefreshableView(new GridView(getContext()));
    }
}