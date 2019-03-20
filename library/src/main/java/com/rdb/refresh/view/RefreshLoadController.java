package com.rdb.refresh.view;

import android.view.View;

public class RefreshLoadController {

    private int loadLayout;

    public RefreshLoadController(int loadLayout) {
        this.loadLayout = loadLayout;
    }

    public final int getLoadLayout() {
        return loadLayout;
    }

    public boolean autoLoad() {
        return true;
    }

    public void initLoadView(View view) {

    }

    public void updateLoadView(View view, boolean loading) {

    }
}
