package com.rdb.refresh;

import android.content.Context;

import java.util.List;

public abstract class RefreshRequest<D> {

    private final RefreshPageProxy refreshPageProxy;

    public RefreshRequest(RefreshPageProxy refreshPageProxy) {
        this.refreshPageProxy = refreshPageProxy;
    }

    protected abstract void doRequest(Context context, int page, int rowCount, D lastData);

    public void notifyRequestStart() {
        refreshPageProxy.onRequestStart();
    }

    public void notifyRequestSuccess(List<D> list) {
        refreshPageProxy.onRequestSuccess(list);
    }

    public void notifyRequestFailure(RefreshRequest.ErrorType errorType) {
        refreshPageProxy.onRequestFailure(errorType);
    }

    public enum ErrorType {
        CLIENT, NETWORK, SERVER
    }
}