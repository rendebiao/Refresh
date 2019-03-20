package com.rdb.refresh;

import android.content.Context;
import android.view.View;

public abstract class RefreshProxy<V extends RefreshContainer, C extends RefreshConfig> implements Refreshable<C> {

    protected Context context;
    protected C refreshConfig;
    protected V refreshContainer;

    public RefreshProxy(Context context) {
        this.context = context;
        refreshConfig = initRefreshConfig();
    }

    public void onCreateView(View view) {
        RefreshMode mode = refreshConfig.refreshMode;
        if (mode == null) {
            mode = RefreshMode.TOP;
        }
        refreshContainer = (V) view.findViewById(refreshConfig.refreshViewId);
        refreshContainer.setMode(mode);
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

    public final V getRefreshContainer() {
        return refreshContainer;
    }

    public final int getLayoutId() {
        return refreshConfig.layoutRes;
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

    @Override
    public void doRefresh() {

    }

    @Override
    public void doLoad() {

    }
}