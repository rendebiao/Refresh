package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public abstract class RefreshContainer<T extends View> extends RefreshLayout {

    protected T refreshableView;
    protected RefreshController refreshController;
    protected LoadController loadController;
    private int downY;
    private int lastY;
    private int distanceY;
    private int touchSlop;
    private float touchX;
    private boolean hasMore;
    private boolean autoLoad;
    private boolean showNoMore;
    private boolean isLoading = false;
    private boolean interceptHorizontal;
    private int mode = TOP;
    private OnLoadListener loadListener;
    private OnRefreshListener refreshListener;

    public RefreshContainer(Context context) {
        this(context, (T) null);
    }

    public RefreshContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshContainer(Context context, T refreshableView) {
        super(context);
        init(context);
        if (refreshableView == null) {
            refreshableView = createRefreshableView();
        }
        setRefreshableView(refreshableView);
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        distanceY = touchSlop * 5;
        super.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                setHasMore(false);
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
            }
        });
        setRefreshLoadController(initLoadController());
        setRefreshController(initRefreshController());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        T view = findRefreshableView();
        if (view == null) {
            view = createRefreshableView();
        }
        setRefreshableView(view);
    }

    protected abstract T findRefreshableView();

    protected abstract T createRefreshableView();

    public void setMode(@Mode int mode) {
        this.mode = mode;
        setEnabled(mode != NONE);
    }

    public void setRefreshLoadController(LoadController loadController) {
        boolean changed = this.loadController != null;
        this.loadController = loadController;
        autoLoad = loadController != null && loadController.autoLoad();
        showNoMore = loadController != null && loadController.showNoMore();
        if (changed) {
            onLoadControllerChanged();
        }
    }

    protected abstract LoadController initLoadController();

    protected abstract RefreshController<T> initRefreshController();

    protected abstract void onLoadControllerChanged();

    protected void setRefreshController(RefreshController refreshController) {
        this.refreshController = refreshController;
        if (refreshController != null) {
            refreshController.init(this, refreshableView);
        }
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public boolean startLoading() {
        if (refreshController.supportLoad()) {
            setLoading(true);
            return true;
        }
        return false;
    }

    public boolean startLoadingDelay(long delay) {
        if (refreshController.supportLoad()) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLoading();
                }
            }, delay);
            return true;
        }
        return false;
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
        if (refreshController.supportLoad() && autoLoad && isBottomEnable() && hasMore && !isRefreshing() && !isLoading) {
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
        return super.canChildScrollUp();
    }

    public T getRefreshableView() {
        return refreshableView;
    }

    public void setRefreshableView(T view) {
        refreshController.init(this, view);
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

    @Override
    public void setRefreshing(boolean refreshing) {
        if (refreshing) {
            setHasMore(false);
        }
        super.setRefreshing(refreshing);
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
        return mode == TOP || mode == BOTH;
    }

    public final boolean isBottomEnable() {
        return mode == BOTTOM || mode == BOTH;
    }

    public final boolean isHasMore() {
        return isBottomEnable() && hasMore;
    }

    private void setHasMore(boolean hasMore) {
        if (isBottomEnable()) {
            this.hasMore = hasMore;
            refreshController.onHasMoreChanged(hasMore);
        }
    }

    public final boolean autoLoad() {
        return autoLoad;
    }

    public final boolean isShowNoMore() {
        return showNoMore;
    }

    @Override
    public void notifyComplete() {
        super.notifyComplete();
        if (isLoading()) {
            setLoading(false);
        }
    }

    public void notifyComplete(boolean hasMore) {
        setHasMore(hasMore);
        notifyComplete();
    }

    public void notifyComplete(boolean hasMore, int delay) {
        setHasMore(hasMore);
        notifyComplete(delay);
    }

    public interface OnLoadListener {
        void onLoad();
    }
}