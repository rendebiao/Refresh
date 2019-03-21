package com.rdb.refresh.view;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class ExpandableListViewController extends AbsListViewController<ExpandableListView> {


    public ExpandableListViewController() {
        super();
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        ExpandableListAdapter adapter = refreshableView.getExpandableListAdapter();
        if (adapter instanceof ExpandableListViewWrapperAdapter) {
            ((ExpandableListViewWrapperAdapter) adapter).setShowLoad(hasMore);
        }
    }

    @Override
    public void onLoadingChanged(boolean loading) {
        ExpandableListAdapter adapter = refreshableView.getExpandableListAdapter();
        if (adapter instanceof BaseExpandableListAdapter) {
            ((BaseExpandableListAdapter) adapter).notifyDataSetChanged();
        }
    }
}
