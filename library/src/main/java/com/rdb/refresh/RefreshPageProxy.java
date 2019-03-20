package com.rdb.refresh;

import android.content.Context;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class RefreshPageProxy<D, V extends RefreshContainer> extends RefreshProxy<V, RefreshListConfig> {

    private View emptyView;
    private RefreshPage refreshPage;
    private ViewStubCompat emptyStub;
    private RefreshRequest<D> refreshRequest;
    private ArrayList<D> dataList = new ArrayList<D>();
    private RefreshEmptyViewController emptyViewController;

    public RefreshPageProxy(Context context) {
        super(context);
        refreshPage = initRefreshPage();
        refreshRequest = initRefreshRequest();
    }

    @Override
    public void onCreateView(View view) {
        super.onCreateView(view);
        View emptyView = view.findViewById(refreshConfig.emptyViewId);
        if (emptyView instanceof ViewStubCompat) {
            emptyStub = (ViewStubCompat) emptyView;
        } else {
            this.emptyView = emptyView;
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化分页配置
     *
     * @return
     */
    protected RefreshPage initRefreshPage() {
        return new RefreshPage(1, 10);
    }

    protected abstract RefreshRequest initRefreshRequest();

    @Override
    public final void doRefresh() {
        refreshPage.curPage = refreshPage.startPage;
        loadData();
    }

    @Override
    public final void doLoad() {
        loadData();
    }

    public abstract void notifyDataSetChanged();

    protected abstract boolean isEmpty();

    private void loadData() {
        refreshRequest.doRequest(context, refreshPage.curPage, refreshPage.rowCount, dataList.size() > 0 ? dataList.get(dataList.size() - 1) : null);
    }

    /**
     * 请求开始
     */
    protected void onRequestStart() {

    }

    /**
     * 请求取消
     */
    protected void onRequestCancel() {

    }

    /**
     * 请求成功 传入数据
     *
     * @param result
     */
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

    /**
     * 处理请求数据
     *
     * @param dataList
     * @param result
     * @param refreshPage
     * @return
     */
    protected boolean onUpdateResult(List<D> dataList, List<D> result, RefreshPage refreshPage) {
        dataList.addAll(result);
        return result.size() >= refreshPage.rowCount;
    }

    /**
     * 请求失败
     */
    protected void onRequestFailure(RefreshRequest.ErrorType errorType) {
        refreshContainer.notifyComplete();
        updateEmptyView(errorType);
    }

    private void updateEmptyView(RefreshRequest.ErrorType errorType) {
        if (dataList.isEmpty()) {
            if (emptyView == null && emptyStub != null) {
                emptyView = emptyStub.inflate();
            }
            if (emptyView != null) {
                emptyView.setVisibility(View.VISIBLE);
                if (emptyViewController != null) {
                    emptyViewController.onEmptyVisible(emptyView, true, errorType);
                }
            }
        } else {
            if (emptyView != null) {
                emptyView.setVisibility(View.GONE);
                if (emptyViewController != null) {
                    emptyViewController.onEmptyVisible(emptyView, false, errorType);
                }
            }
        }
    }

    public final ArrayList<D> getDataList() {
        return dataList;
    }

    public final int getRowCount() {
        return refreshPage.rowCount;
    }

    public final int getCurPage() {
        return refreshPage.curPage;
    }

    public abstract void scrollToTop();

    public void setEmptyViewController(RefreshEmptyViewController emptyController) {
        this.emptyViewController = emptyController;
    }
}
