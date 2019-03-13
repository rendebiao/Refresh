package com.rdb.refresh;

public abstract class RefreshController {

    protected RefreshContainer container;

    protected abstract boolean supportLoad();

    protected abstract boolean canChildScrollUp();

    protected abstract boolean isReadyForLoading();

    protected abstract void onHasMoreChanged(boolean hasMore);

    protected abstract void onLoadingChanged(boolean loading);
}

