package com.rdb.refresh.view;

import android.view.View;
import android.widget.ScrollView;

public class ViewController extends RefreshController<View> {

    public ViewController() {
        super();
    }

    @Override
    protected final boolean supportLoad() {
        return false;
    }

    @Override
    protected boolean canChildScrollUp() {
        if (refreshableView instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) refreshableView;
            return scrollView.getScrollY() > 0;
        }
        return false;
    }

    @Override
    protected boolean isReadyForLoading() {
        return false;
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {

    }

    @Override
    protected void onLoadingChanged(boolean loading) {

    }
}
