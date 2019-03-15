package com.rdb.refresh.abslist;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class RefreshExpandableListViewController extends RefreshAbsListViewController {

    private ExpandableListView listView;

    public RefreshExpandableListViewController(ExpandableListView listView) {
        super(listView);
        this.listView = listView;
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        ExpandableListAdapter adapter = listView.getExpandableListAdapter();
        if (adapter instanceof RefreshExpandableListViewAdapter) {
            ((RefreshExpandableListViewAdapter) adapter).setShowLoad(hasMore);
        }
    }

    @Override
    public void onLoadingChanged(boolean loading) {
        ExpandableListAdapter adapter = listView.getExpandableListAdapter();
        if (adapter instanceof BaseExpandableListAdapter) {
            ((BaseExpandableListAdapter) adapter).notifyDataSetChanged();
        }
    }
}
