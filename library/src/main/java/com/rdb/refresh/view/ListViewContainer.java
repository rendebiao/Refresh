package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.rdb.refresh.Refresh;

public class ListViewContainer extends AbsListViewContainer<ListView> {

    public ListViewContainer(Context context) {
        super(context);
    }

    public ListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewContainer(Context context, ListView refreshableView) {
        super(context, refreshableView);
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

    @Override
    protected LoadController initLoadController() {
        return Refresh.getListLoadController();
    }
}