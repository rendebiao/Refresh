package com.rdb.refresh.view;

import android.view.View;

public abstract class RefreshController<T extends View> {

    protected T refreshableView;
    protected RefreshContainer container;

    public RefreshController() {
    }

    void init(RefreshContainer container, T refreshableView) {
        this.container = container;
        onRefreshableViewChanged(this.refreshableView, refreshableView);
        this.refreshableView = refreshableView;
    }

    protected void onRefreshableViewChanged(T oldView, T newView) {

    }

    protected abstract boolean supportLoad();

    protected abstract boolean canChildScrollUp();

    protected abstract boolean isReadyForLoading();

    protected abstract void onHasMoreChanged(boolean hasMore);

    protected abstract void onLoadingChanged(boolean loading);

    protected abstract void onRefreshing(boolean refreshing);
}

