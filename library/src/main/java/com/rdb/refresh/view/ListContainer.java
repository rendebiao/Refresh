package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.rdb.refresh.Refresh;

public class ListContainer extends AbsListContainer<ListView> {

    public ListContainer(Context context) {
        super(context);
    }

    public ListContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListContainer(Context context, ListView refreshableView) {
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