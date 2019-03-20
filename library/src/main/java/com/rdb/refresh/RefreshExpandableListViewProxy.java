package com.rdb.refresh;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.rdb.refresh.view.RefreshExpandableListViewContainer;

import java.util.ArrayList;

public abstract class RefreshExpandableListViewProxy<D> extends RefreshPageProxy<D, RefreshExpandableListViewContainer> {

    protected ExpandableListView listView;
    protected BaseExpandableListAdapter adapter;

    public RefreshExpandableListViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest) {
        super(context, refreshListConfig, refreshRequest);
        listView = refreshContainer.getRefreshableView();
        adapter = createAdapter(context, getDataList());
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected RefreshExpandableListViewContainer createRefreshContainer(Context context) {
        return new RefreshExpandableListViewContainer(context);
    }

    protected abstract BaseExpandableListAdapter createAdapter(Context context, ArrayList<D> items);

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.isEmpty();
    }

    public ListView getExpandableListView() {
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
