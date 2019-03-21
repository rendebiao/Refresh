package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ViewContainer extends RefreshContainer<View> {

    public ViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewContainer(Context context, View refreshableView) {
        super(context, refreshableView);
    }

    @Override
    protected View findRefreshableView() {
        int count = getChildCount();
        if (count > 1) {
            return getChildAt(1);
        }
        return null;
    }

    @Override
    protected View createRefreshableView() {
        return null;
    }

    @Override
    protected LoadController initLoadController() {
        return null;
    }

    @Override
    protected RefreshController initRefreshController() {
        return new ViewController();
    }

    @Override
    protected void onLoadControllerChanged() {

    }
}