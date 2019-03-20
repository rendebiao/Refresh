package com.rdb.refresh;

import android.view.View;

public interface RefreshEmptyViewController {
    void onEmptyVisible(View emptyView, boolean visible, RefreshRequest.ErrorType errorType);
}
