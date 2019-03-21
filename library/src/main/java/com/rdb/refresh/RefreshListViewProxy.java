package com.rdb.refresh;

import android.content.Context;
import android.widget.ListView;

import com.rdb.refresh.view.ListViewContainer;

public class RefreshListViewProxy<D> extends RefreshPageProxy<D, ListViewContainer> {

    protected ListView listView;
    protected RefreshBaseAdapter<D> adapter;

    public RefreshListViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest, RefreshBaseAdapter<D> adapter) {
        super(context, refreshListConfig, refreshRequest);
        listView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected ListViewContainer createRefreshContainer(Context context) {
        return new ListViewContainer(context);
    }

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.isEmpty();
    }

    public ListView getRefreshableView() {
        return listView;
    }

    public RefreshBaseAdapter<D> getAdapter() {
        return adapter;
    }

    @Override
    public final void scrollToTop() {
        if (listView != null) {
            if (listView.getFirstVisiblePosition() > 10) {
                listView.setSelection(5);
            }
            listView.smoothScrollToPosition(0);
        }
    }

    @Override
    public final void scrollToBottom() {
        if (listView != null) {
            listView.setSelection(Integer.MAX_VALUE);
        }
    }

}
