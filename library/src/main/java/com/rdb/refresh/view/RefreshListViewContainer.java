package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.rdb.refresh.Refresh;

public class RefreshListViewContainer extends RefreshAbsListViewContainer<ListView> {

    public RefreshListViewContainer(Context context) {
        super(context);
        setRefreshLoadController(Refresh.getListLoadController());
    }

    public RefreshListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshLoadController(Refresh.getListLoadController());
    }

    public RefreshListViewContainer(Context context, ListView refreshableView) {
        super(context, refreshableView);
        setRefreshLoadController(Refresh.getListLoadController());
    }

    @Override
    protected ListView findRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ListView) {
                return (ListView) child;
            }
        }
        return null;
    }

    @Override
    protected ListView createRefreshableView() {
        return new ListView(getContext());
    }
}