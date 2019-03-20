package com.rdb.refresh;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.rdb.refresh.view.RefreshLayout;

public class RefreshConfig {

    int layoutRes;
    int refreshViewId;
    int refreshMode;

    public RefreshConfig(@RefreshLayout.Mode int refreshMode) {
        this.refreshMode = refreshMode;
    }

    public RefreshConfig(@RefreshLayout.Mode int refreshMode, @LayoutRes int layoutRes, @IdRes int refreshViewId) {
        this.refreshMode = refreshMode;
        this.layoutRes = layoutRes;
        this.refreshViewId = refreshViewId;
    }
}
