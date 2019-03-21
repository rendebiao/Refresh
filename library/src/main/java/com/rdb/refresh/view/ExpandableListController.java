package com.rdb.refresh.view;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class ExpandableListController extends AbsListController<ExpandableListView> {


    public ExpandableListController() {
        super();
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        ExpandableListAdapter adapter = refreshableView.getExpandableListAdapter();
        if (adapter instanceof ExpandableListWrapperAdapter) {
            ((ExpandableListWrapperAdapter) adapter).setShowLoad(hasMore);
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
