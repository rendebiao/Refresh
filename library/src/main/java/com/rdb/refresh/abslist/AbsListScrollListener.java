package com.rdb.refresh.abslist;

import android.widget.AbsListView;

import java.util.HashSet;
import java.util.Set;

class AbsListScrollListener implements AbsListView.OnScrollListener {

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
