package com.rdb.refresh;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;

import com.rdb.refresh.view.RefreshContainer;

public abstract class RefreshProxy<V extends RefreshContainer, C extends RefreshConfig> {

    private View view;
    protected Context context;
    protected C refreshConfig;
    protected V refreshContainer;

    public RefreshProxy(Context context, C refreshConfig) {
        this.context = context;
        this.refreshConfig = refreshConfig;
        int layoutId = refreshConfig.layoutRes;
        if (layoutId > 0) {
            view = LayoutInflater.from(context).inflate(layoutId, null);
        }
        if (view == null) {
            view = refreshContainer = createRefreshContainer(context);
        } else {
            refreshContainer = (V) view.findViewById(refreshConfig.refreshViewId);
        }
        refreshContainer.setMode(refreshConfig.refreshMode);
        if (refreshContainer.isTopEnable()) {
            refreshContainer.setOnRefreshListener(new RefreshContainer.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    doRefresh();
                }
            });
        }
        if (refreshContainer.isBottomEnable()) {
            refreshContainer.setOnLoadListener(new RefreshContainer.OnLoadListener() {
                @Override
                public void onLoad() {
                    doLoad();
                }
            });
        }
    }

    public View getView() {
        return view;
    }

    public final <T extends View> T findViewById(@IdRes int id) {
        return view.findViewById(id);
    }

    protected abstract V createRefreshContainer(Context context);

    protected abstract void doRefresh();

    protected abstract void doLoad();

    public final V getRefreshContainer() {
        return refreshContainer;
    }

    public void startRefreshing() {
        if (refreshContainer != null) {
            refreshContainer.startRefreshing(true);
        }
    }

    public void startRefreshingDelay(long delay) {
        if (refreshContainer != null) {
            refreshContainer.startRefreshingDelay(delay, true);
        }
    }

    public void startLoading() {
        if (refreshContainer != null) {
            refreshContainer.startLoading();
        }
    }

    public void startLoadingDelay(long delay) {
        if (refreshContainer != null) {
            refreshContainer.startLoadingDelay(delay);
        }
    }
}