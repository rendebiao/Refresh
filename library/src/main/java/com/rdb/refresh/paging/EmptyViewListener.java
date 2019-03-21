package com.rdb.refresh.paging;

import android.view.View;

public interface EmptyViewListener {
    void onEmptyVisible(View emptyView, boolean visible, Request.Error error);
}
