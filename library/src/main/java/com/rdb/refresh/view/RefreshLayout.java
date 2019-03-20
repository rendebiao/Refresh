package com.rdb.refresh.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.TypedValue;

public class RefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    public static final int NONE = 0;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int BOTH = 3;

    private OnRefreshListener refreshListener;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorAccent, typedValue, true);
        setColorSchemeColors(typedValue.data);
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

    @IntDef({NONE, TOP, BOTTOM, BOTH})
    public @interface Mode {

    }
}
