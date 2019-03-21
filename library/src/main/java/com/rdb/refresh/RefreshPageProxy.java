package com.rdb.refresh;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;

import com.rdb.refresh.view.Container;

import java.util.ArrayList;
import java.util.List;

public abstract class RefreshPageProxy<D, V extends Container> extends RefreshProxy<V, RefreshListConfig> {

    private View emptyView;
    private RefreshPage refreshPage;
    private ViewStub emptyStub;
    private RefreshRequest<D> refreshRequest;
    private ArrayList<D> dataList = new ArrayList<D>();
    private RefreshEmptyViewController emptyViewController;

    public RefreshPageProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest) {
        super(context, refreshListConfig);
        refreshPage = initRefreshPage();
        this.refreshRequest = refreshRequest;
        if (refreshRequest != null) {
            refreshRequest.setRefreshRequest(this);
        }
        View emptyView = findViewById(refreshConfig.emptyViewId);
        if (emptyView instanceof ViewStub) {
            emptyStub = (ViewStub) emptyView;
        } else {
            this.emptyView = emptyView;
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    protected RefreshPage initRefreshPage() {
        return new RefreshPage(1, 10);
    }

    @Override
    protected final void doRefresh() {
        refreshPage.curPage = refreshPage.startPage;
        loadData();
    }

    @Override
    protected final void doLoad() {
        loadData();
    }

    public abstract void notifyDataSetChanged();

    protected abstract boolean isEmpty();

    private void loadData() {
        if (refreshRequest == null) {
            throw new RuntimeException("unset RefreshRequest");
        } else {
            refreshRequest.doRequest(context, refreshPage.curPage, refreshPage.rowCount);
        }
    }

    protected void onRequestStart() {

    }

    protected void onRequestCancel() {

    }

    protected final void onRequestSuccess(List<D> result) {
        if (refreshPage.curPage == 1) {
            dataList.clear();//清空以前的数据
        }
        refreshPage.curPage++;
        boolean hasMore = onUpdateResult(dataList, result, refreshPage);
        notifyDataSetChanged();
        if (refreshPage.curPage == 2 && dataList.size() > 0) {
            scrollToTop();
        }
        refreshContainer.notifyComplete(hasMore);
        updateEmptyView(null);
    }

    protected boolean onUpdateResult(List<D> dataList, List<D> result, RefreshPage refreshPage) {
        dataList.addAll(result);
        return result.size() >= refreshPage.rowCount;
    }

    protected void onRequestFailure(RefreshRequestError error) {
        refreshContainer.notifyComplete();
        updateEmptyView(error);
    }

    private void updateEmptyView(RefreshRequestError error) {
        if (dataList.isEmpty()) {
            if (emptyView == null && emptyStub != null) {
                emptyView = emptyStub.inflate();
            }
            if (emptyView != null) {
                emptyView.setVisibility(View.VISIBLE);
                if (emptyViewController != null) {
                    emptyViewController.onEmptyVisible(emptyView, true, error);
                }
            }
        } else {
            if (emptyView != null) {
                emptyView.setVisibility(View.GONE);
                if (emptyViewController != null) {
                    emptyViewController.onEmptyVisible(emptyView, false, error);
                }
            }
        }
    }

    public final ArrayList<D> getDataList() {
        return dataList;
    }

    public final int getCurPage() {
        return refreshPage.curPage;
    }

    public abstract void scrollToTop();

    public abstract void scrollToBottom();

    public void setEmptyViewController(RefreshEmptyViewController emptyController) {
        this.emptyViewController = emptyController;
    }
}
