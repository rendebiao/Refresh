package com.rdb.refresh;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.rdb.refresh.view.ExpandableListViewContainer;

public class RefreshExpandableListViewProxy<D> extends RefreshPageProxy<D, ExpandableListViewContainer> {

    protected ExpandableListView listView;
    protected RefreshBaseExpandableListAdapter<D> adapter;

    public RefreshExpandableListViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest, RefreshBaseExpandableListAdapter<D> adapter) {
        super(context, refreshListConfig, refreshRequest);
        listView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected ExpandableListViewContainer createRefreshContainer(Context context) {
        return new ExpandableListViewContainer(context);
    }

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.isEmpty();
    }

    public ExpandableListView getRefreshableView() {
        return listView;
    }

    public BaseExpandableListAdapter getAdapter() {
        return adapter;
    }

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
