package com.rdb.refresh.view;

import android.view.View;

public class LoadController {

    private final int loadLayout;

    public LoadController(int loadLayout) {
        this.loadLayout = loadLayout;
    }

    public final int getLoadLayout() {
        return loadLayout;
    }

    public boolean autoLoad() {
        return true;
    }

    public boolean showNoMore() {
        return false;
    }

    public void initLoadView(View view) {

    }

    public void updateLoadView(View view, boolean loading, boolean hasMore) {

    }
}
