package com.rdb.refresh;

public interface Refreshable<C extends RefreshConfig> {

    C initRefreshConfig();

    void doRefresh();

    void doLoad();
}
