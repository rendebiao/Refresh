package com.rdb.refresh;

import android.content.Context;

import java.util.List;

public abstract class RefreshRequest<D> {

    private RefreshPageProxy refreshPageProxy;

    protected final void setRefreshRequest(RefreshPageProxy refreshPageProxy) {
        this.refreshPageProxy = refreshPageProxy;
    }

    protected abstract void doRequest(Context context, int page, int rowCount);

    public void notifyRequestStart() {
        if (refreshPageProxy != null) {
            refreshPageProxy.onRequestStart();
        }
    }

    public void notifyRequestSuccess(List<D> list) {
        if (refreshPageProxy != null) {
            refreshPageProxy.onRequestSuccess(list);
        }
    }

    public void notifyRequestFailure(RefreshRequestError error) {
        if (refreshPageProxy != null) {
            refreshPageProxy.onRequestFailure(error);
        }
    }

    public void notifyRequestCancel() {
        if (refreshPageProxy != null) {
            refreshPageProxy.onRequestCancel();
        }
    }

}