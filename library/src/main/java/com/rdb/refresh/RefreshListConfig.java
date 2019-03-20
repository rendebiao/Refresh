package com.rdb.refresh;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.rdb.refresh.view.RefreshLayout;

public class RefreshListConfig extends RefreshConfig {

    int emptyViewId;

    public RefreshListConfig(@RefreshLayout.Mode int refreshMode) {
        super(refreshMode);
    }

    public RefreshListConfig(@RefreshLayout.Mode int refreshMode, @LayoutRes int layoutRes, @IdRes int refreshViewId) {
        super(refreshMode, layoutRes, refreshViewId);
    }

    public RefreshListConfig(@RefreshLayout.Mode int mode, @LayoutRes int layoutRes, @IdRes int refreshViewId, @IdRes int emptyViewId) {
        super(mode, layoutRes, refreshViewId);
        this.emptyViewId = emptyViewId;
    }
}

