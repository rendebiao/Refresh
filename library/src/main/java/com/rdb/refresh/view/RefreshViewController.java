package com.rdb.refresh.view;

import android.view.View;
import android.widget.ScrollView;

import com.rdb.refresh.RefreshController;

public class RefreshViewController extends RefreshController {

    private View view;

    public RefreshViewController(View view) {
        this.view = view;
    }

    @Override
    protected final boolean supportLoad() {
        return false;
    }

    @Override
    protected boolean canChildScrollUp() {
        if (view instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) view;
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
