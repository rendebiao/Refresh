package com.rdb.refresh.view;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class ExpListController extends AbsListController<ExpandableListView> {


    public ExpListController() {
        super();
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        if (!container.isShowNoMore()) {
            ExpandableListAdapter adapter = refreshableView.getExpandableListAdapter();
            if (adapter instanceof ExpListAdapter) {
                ((ExpListAdapter) adapter).setShowLoad(hasMore);
            }
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
