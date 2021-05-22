package com.rdb.refresh.paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;

import com.rdb.refresh.view.RefreshContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class Paging<D, V extends RefreshContainer> {

    private final Request<D> request;
    private final ArrayList<D> dataList = new ArrayList<D>();
    protected Context context;
    protected Config config;
    protected V refreshContainer;
    private int curPage;
    private View view;
    private View emptyView;
    private boolean unloadEmpty;
    private EmptyViewListener emptyViewListener;

    public Paging(Context context, Config config, Request request) {
        this.context = context;
        this.config = config;
        this.request = request;
        if (request != null) {
            request.setPaging(this);
        }
        initView();
    }

    private void initView() {
        if (config.type == Config.NULL) {
            view = refreshContainer = createRefreshContainer(context);
        } else if (config.type == Config.EMPTY) {
            refreshContainer = createRefreshContainer(context);
            FrameLayout frameLayout = new FrameLayout(context);
            view = frameLayout;
            frameLayout.addView(refreshContainer, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            unloadEmpty = true;
        } else if (config.type == Config.LAYOUT) {
            view = LayoutInflater.from(context).inflate(config.layoutRes, null);
            refreshContainer = findViewById(config.refreshViewId);
        } else if (config.type == Config.LAYOUT_EMPTY) {
            view = LayoutInflater.from(context).inflate(config.layoutRes, null);
            refreshContainer = findViewById(config.refreshViewId);
            View emptyView = findViewById(config.emptyViewId);
            if (emptyView instanceof ViewStub) {
                unloadEmpty = true;
            } else {
                this.emptyView = emptyView;
            }
        } else if (config.type == Config.LAYOUT_STUB_EMPTY) {
            view = LayoutInflater.from(context).inflate(config.layoutRes, null);
            refreshContainer = findViewById(config.refreshViewId);
            unloadEmpty = true;
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
        refreshContainer.setMode(config.refreshMode);
        if (refreshContainer.isTopEnable()) {
            refreshContainer.setOnRefreshListener(new RefreshContainer.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    doRefresh();
                }
            });
        }
        if (refreshContainer.isBottomEnable()) {
            refreshContainer.setOnLoadListener(new RefreshContainer.OnLoadListener() {
                @Override
                public void onLoad() {
                    doLoad();
                }
            });
        }
    }

    protected abstract V createRefreshContainer(Context context);

    protected final void doRefresh() {
        curPage = config.startPage;
        loadData();
    }

    protected final void doLoad() {
        loadData();
    }

    private void loadData() {
        if (request == null) {
            throw new RuntimeException("unset Request");
        } else {
            request.doRequest(context, curPage, config.rowCount);
        }
    }

    protected void onRequestStart() {

    }

    protected void onRequestCancel() {

    }

    protected final void onRequestSuccess(List<D> result) {
        if (curPage == config.startPage) {
            dataList.clear();//清空以前的数据
        }
        curPage++;
        boolean hasMore = onUpdateResult(dataList, result);
        notifyDataSetChanged();
        if (curPage == config.startPage + 1 && dataList.size() > 0) {
            scrollToTop();
        }
        refreshContainer.notifyComplete(hasMore);
        updateEmptyView(null);
    }

    protected boolean onUpdateResult(List<D> dataList, List<D> result) {
        dataList.addAll(result);
        return result.size() >= config.rowCount;
    }

    protected void onRequestFailure(Request.Error error) {
        refreshContainer.notifyComplete();
        updateEmptyView(error);
    }

    private void updateEmptyView(Request.Error error) {
        if (dataList.isEmpty()) {
            if (unloadEmpty) {
                if (config.type == Config.EMPTY) {
                    emptyView = LayoutInflater.from(context).inflate(config.emptyLayoutRes, null);
                    if (emptyView != null) {
                        ((FrameLayout) view).addView(emptyView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                } else if (config.type == Config.LAYOUT_EMPTY) {
                    if (emptyView instanceof ViewStub) {
                        emptyView = ((ViewStub) emptyView).inflate();
                    }
                } else if (config.type == Config.LAYOUT_STUB_EMPTY) {
                    ViewStub emptyStub = findViewById(config.emptyStubId);
                    if (emptyStub != null) {
                        emptyStub.setLayoutResource(config.emptyLayoutRes);
                        emptyView = emptyStub.inflate();
                    }
                }
                unloadEmpty = false;
            }
            if (emptyView != null) {
                emptyView.setVisibility(View.VISIBLE);
                if (emptyViewListener != null) {
                    emptyViewListener.onEmptyVisible(emptyView, true, error);
                }
            }
        } else {
            if (emptyView != null) {
                emptyView.setVisibility(View.GONE);
                if (emptyViewListener != null) {
                    emptyViewListener.onEmptyVisible(emptyView, false, error);
                }
            }
        }
    }


    public View getView() {
        return view;
    }

    public final V getRefreshContainer() {
        return refreshContainer;
    }

    public final <T extends View> T findViewById(@IdRes int id) {
        return view.findViewById(id);
    }

    protected abstract boolean isEmpty();

    public final ArrayList<D> getDataList() {
        return dataList;
    }

    public final int getCurPage() {
        return curPage;
    }

    public final int getRowCount() {
        return config.rowCount;
    }

    public abstract void notifyDataSetChanged();

    public void startRefreshing() {
        if (refreshContainer != null) {
            refreshContainer.startRefreshing(true);
        }
    }

    public void startRefreshingDelay(long delay) {
        if (refreshContainer != null) {
            refreshContainer.startRefreshingDelay(delay, true);
        }
    }

    public void startLoading() {
        if (refreshContainer != null) {
            refreshContainer.startLoading();
        }
    }

    public void startLoadingDelay(long delay) {
        if (refreshContainer != null) {
            refreshContainer.startLoadingDelay(delay);
        }
    }

    public abstract void scrollToTop();

    public abstract void scrollToBottom();

    public void setEmptyViewListener(EmptyViewListener emptyViewListener) {
        this.emptyViewListener = emptyViewListener;
    }
}
