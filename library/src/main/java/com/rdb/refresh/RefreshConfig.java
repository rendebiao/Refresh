package com.rdb.refresh;

public class RefreshConfig {

    int layoutRes;
    int refreshViewId;
    RefreshMode refreshMode;

    public RefreshConfig(RefreshMode refreshMode, int layoutRes, int refreshViewId) {
        if (refreshMode == null || refreshViewId <= 0) {
            throw new RuntimeException("RefreshConfig 参数不合法");
        }
        this.refreshMode = refreshMode;
        this.layoutRes = layoutRes;
        this.refreshViewId = refreshViewId;
    }
}
