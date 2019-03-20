package com.rdb.refresh;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;

public abstract class Refresh<V extends RefreshContainer, C extends RefreshConfig, P extends RefreshProxy<V, C>> {

    private View view;
    private V refreshContainer;
    private P refreshProxy;

    public Refresh(Context context) {
        refreshProxy = createRefreshProxy();
        view = LayoutInflater.from(context).inflate(refreshProxy.getLayoutId(), null);
        refreshProxy.onCreateView(view);
        refreshContainer = refreshProxy.getRefreshContainer();
    }

    public View getView() {
        return view;
    }

    public final <T extends View> T findViewById(@IdRes int id) {
        return view.findViewById(id);
    }

    protected abstract P createRefreshProxy();

    public final P getRefreshProxy() {
        return refreshProxy;
    }

    public V getRefreshContainer() {
        return refreshContainer;
    }

    public void startRefreshing() {
        refreshProxy.startRefreshing();
    }

    public void startRefreshingDelay(long delay) {
        refreshProxy.startRefreshingDelay(delay);
    }

    public void startLoading() {
        refreshProxy.startLoading();
    }

    public void startLoadingDelay(long delay) {
        refreshProxy.startLoadingDelay(delay);
    }
}
