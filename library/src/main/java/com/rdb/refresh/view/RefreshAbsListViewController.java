package com.rdb.refresh.view;

import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import java.util.HashSet;
import java.util.Set;

public class RefreshAbsListViewController extends RefreshController {

    private AbsListView listView;
    private AbsListScrollListener absListScrollListener = new AbsListScrollListener();

    public RefreshAbsListViewController(AbsListView listView) {
        this.listView = listView;
        listView.setOnScrollListener(absListScrollListener);
        absListScrollListener.addOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (container != null && container.autoLoad() && container.isHasMore() && container.isBottomEnable() && !container.isRefreshing() && !container.isLoading() && !isScrollTop() && isReadyForLoading()) {
                    container.startLoading();
                }
            }
        });
    }

    public final void setOnScrollListener(AbsListView.OnScrollListener listener) {
        absListScrollListener.setOnScrollListener(listener);
    }

    public void addOnScrollListener(AbsListView.OnScrollListener listener) {
        absListScrollListener.addOnScrollListener(listener);
    }

    public void removeOnScrollListener(AbsListView.OnScrollListener listener) {
        absListScrollListener.removeOnScrollListener(listener);
    }

    @Override
    protected final boolean supportLoad() {
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
                    return itemLocation[1] <= listLocation[1] + listView.getHeight() - listView.getPaddingBottom();
                }
            }
        }
        return false;
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        ListAdapter adapter = listView.getAdapter();
        while (adapter instanceof WrapperListAdapter) {
            adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
        }
        if (adapter instanceof RefreshAbsListViewAdapter) {
            ((RefreshAbsListViewAdapter) adapter).setShowLoad(hasMore);
        }
    }

    @Override
    public void onLoadingChanged(boolean loading) {
        ListAdapter adapter = listView.getAdapter();
        while (adapter instanceof WrapperListAdapter) {
            adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
        }
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


    static class AbsListScrollListener implements AbsListView.OnScrollListener {

        private AbsListView.OnScrollListener setListener;
        private Set<AbsListView.OnScrollListener> scrollListeners = new HashSet<>();

        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            if (setListener != null) {
                setListener.onScrollStateChanged(absListView, scrollState);
            }
            for (AbsListView.OnScrollListener listener : scrollListeners) {
                listener.onScrollStateChanged(absListView, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (setListener != null) {
                setListener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            for (AbsListView.OnScrollListener listener : scrollListeners) {
                listener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }

        public void setOnScrollListener(AbsListView.OnScrollListener listener) {
            setListener = listener;
        }

        public void addOnScrollListener(AbsListView.OnScrollListener listener) {
            scrollListeners.add(listener);
        }

        public void removeOnScrollListener(AbsListView.OnScrollListener listener) {
            scrollListeners.remove(listener);
        }
    }
}
