package com.rdb.refresh;

public class RefreshListConfig extends RefreshConfig {

    int emptyViewId;

    public RefreshListConfig(RefreshMode mode, int layoutRes, int refreshViewId, int emptyViewId) {
        super(mode, layoutRes, refreshViewId);
        if (mode == null) {
            throw new RuntimeException("RefreshListConfig 参数不合法");
        }
        this.emptyViewId = emptyViewId;
    }
}

