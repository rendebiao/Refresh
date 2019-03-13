package com.rdb.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;

public abstract class RefreshContainer<T extends View> extends RefreshLayout {

    protected LoadController loadController;
    protected RefreshController refreshController;
    private int downY;
    private int lastY;
    private int distanceY;
    private int touchSlop;
    private float touchX;
    private boolean hasMore;
    private T refreshableView;
    private boolean isLoading = false;
    private OnLoadListener loadListener;
    private boolean interceptHorizontal;
    private RefreshMode mode = RefreshMode.TOP;

    public RefreshContainer(Context context) {
        this(context, null);
    }

    public RefreshContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        distanceY = touchSlop * 5;
        setRefreshableView(createRefreshableView(context));
    }

    public void setMode(RefreshMode mode) {
        this.mode = mode;
        setEnabled(mode != RefreshMode.NONE);
    }

    public void setLoadController(LoadController loadController) {
        this.loadController = loadController;
    }

    protected void setRefreshController(RefreshController refreshController) {
        this.refreshController = refreshController;
        if (refreshController != null) {
            refreshController.container = this;
        }
    }

    public void startLoading() {
        setLoading(true);
    }

    public void startLoadingDelay(long delay) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoading();
            }
        }, delay);
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public void setInterceptHorizontal(boolean interceptHorizontal) {
        this.interceptHorizontal = interceptHorizontal;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (interceptHorizontal) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchX = ev.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(ev.getX() - touchX) > touchSlop) {
                        return false;
                    }
            }
        }
        return isTopEnable() && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (isBottomEnable() && hasMore && !isRefreshing() && !isLoading) {
            final int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downY = (int) event.getRawY();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    if ((downY - lastY) >= distanceY && refreshController != null && refreshController.isReadyForLoading()) {
                        setLoading(true);
                    }
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean canChildScrollUp() {
        if (refreshController != null) {
            return refreshController.canChildScrollUp();
        }
        if (refreshableView instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) refreshableView;
            return scrollView.getScrollY() > 0;
        }
        return super.canChildScrollUp();
    }

    protected abstract T createRefreshableView(Context context);

    public T getRefreshableView() {
        return refreshableView;
    }

    public void setRefreshableView(T view) {
        if (refreshableView != null) {
            removeView(refreshableView);
        }
        if (view != null) {
            refreshableView = view;
            int childCount = getChildCount();
            boolean inLayout = false;
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i) == view) {
                    inLayout = true;
                    break;
                }
            }
            if (!inLayout) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeView(view);
                }
                addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
        }
    }

    public final boolean isLoading() {
        return isLoading;
    }

    private void setLoading(boolean loading) {
        if (isLoading != loading) {
            isLoading = loading;
            try {
                if (isLoading) {
                    refreshController.onLoadingChanged(true);
                    if (loadListener != null) {
                        loadListener.onLoad();
                    }
                } else {
                    downY = 0;
                    lastY = 0;
                    refreshController.onLoadingChanged(false);
                }
                if (refreshController != null) {
                    refreshController.onLoadingChanged(isLoading);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final boolean isTopEnable() {
        return mode == RefreshMode.TOP || mode == RefreshMode.BOTH;
    }

    public final boolean isBottomEnable() {
        return mode == RefreshMode.BOTTOM || mode == RefreshMode.BOTH;
    }

    public final boolean isHasMore() {
        return isBottomEnable() && hasMore;
    }

    @Override
    public void notifyComplete() {
        super.notifyComplete();
        if (isLoading()) {
            setLoading(false);
        }
    }

    public void notifyComplete(boolean hasMore) {
        super.notifyComplete();
        if (isBottomEnable()) {
            this.hasMore = hasMore;
            refreshController.onHasMoreChanged(hasMore);
        }
        if (isLoading()) {
            setLoading(false);
        }
    }

    public void notifyComplete(boolean hasMore, int delay) {
        super.notifyComplete(delay);
        if (isBottomEnable()) {
            this.hasMore = hasMore;
            refreshController.onHasMoreChanged(hasMore);
        }
    }

    public interface OnLoadListener {
        void onLoad();
    }
}