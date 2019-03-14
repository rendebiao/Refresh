package com.rdb.refresh.abslist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.rdb.refresh.RefreshLoadController;

public class RefreshListViewContainer extends RefreshAbsListViewContainer<ListView> {

    private static RefreshLoadController defaultLoadController;

    public RefreshListViewContainer(Context context) {
        super(context);
        setRefreshLoadController(defaultLoadController);
    }

    public RefreshListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshLoadController(defaultLoadController);
    }

    public static void setDefaultRefreshLoadController(RefreshLoadController defaultLoadController) {
        RefreshListViewContainer.defaultLoadController = defaultLoadController;
    }

    @Override
    protected void findAndInitRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ListView) {
                setRefreshableView((ListView) child);
                return;
            }
        }
        setRefreshableView(new ListView(getContext()));
    }
}