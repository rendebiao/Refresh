package com.rdb.refresh.list;

import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.rdb.refresh.RefreshController;

public class RefreshListController extends RefreshController {

    private AbsListView listView;
    private AbsListView.OnScrollListener scrollListener;

    public RefreshListController(AbsListView listView) {
        this.listView = listView;
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollListener != null) {
                    scrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (scrollListener != null) {
                    scrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                if (container != null && container.isHasMore() && container.isBottomEnable() && !container.isRefreshing() && !container.isLoading() && !isScrollTop() && isReadyForLoading()) {
                    container.startLoading();
                }
            }
        });
    }

    public void setOnScrollListener(AbsListView.OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    @Override
    protected boolean supportLoad() {
        return true;
    }

    @Override
    public boolean canChildScrollUp() {
        return !isScrollTop();
    }

    @Override
    public boolean isReadyForLoading() {
        if (listView != null && listView.getAdapter() != null && listView.getAdapter().getCount() > 0) {
            if (listView.getLastVisiblePosition() == (listView.getAdapter().getCount() - 1)) {
                View item = listView.getChildAt(listView.getChildCount() - 1);
                if (item != null) {
                    int[] itemLocation = new int[2];
                    int[] listLocation = new int[2];
                    item.getLocationOnScreen(itemLocation);
                    listView.getLocationOnScreen(listLocation);
                    return itemLocation[1] + item.getHeight() <= listLocation[1] + listView.getHeight() - listView.getPaddingBottom();
                }
            }
        }
        return false;
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter instanceof RefreshListAdapter) {
            ((RefreshListAdapter) adapter).setShowLoad(hasMore);
        }
    }

    @Override
    public void onLoadingChanged(boolean loading) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter instanceof BaseAdapter) {
            ((BaseAdapter) adapter).notifyDataSetChanged();
        }
    }

    private boolean isScrollTop() {
        if (listView.getChildCount() > 0) {
            return listView.getFirstVisiblePosition() == 0
                    && listView.getChildAt(0).getTop() == listView.getPaddingTop();
        }
        return true;
    }
}
