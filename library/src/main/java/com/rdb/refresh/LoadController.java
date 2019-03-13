package com.rdb.refresh;

import android.view.View;

public interface LoadController {

    int getLoadLayout();

    void updateLoadView(View view, boolean loading);
}
