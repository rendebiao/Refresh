package com.rdb.refresh;

import android.content.Context;
import android.util.AttributeSet;

public class RefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    private OnRefreshListener refreshListener;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
        refreshListener = listener;
    }

    public void startRefreshing(boolean notify) {
        if (!isRefreshing()) {
            setRefreshing(true);
            if (notify && refreshListener != null) {
                refreshListener.onRefresh();
            }
        }
    }

    public void startRefreshingDelay(long delay, final boolean notify) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startRefreshing(notify);
            }
        }, delay);
    }

    public void notifyComplete() {
        if (isRefreshing()) {
            setRefreshing(false);
        }
    }

    public void notifyComplete(int delay) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyComplete();
            }
        }, delay);
    }
}
