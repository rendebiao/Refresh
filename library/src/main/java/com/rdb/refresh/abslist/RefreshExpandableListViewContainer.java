package com.rdb.refresh.abslist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.rdb.refresh.RefreshContainer;
import com.rdb.refresh.RefreshLoadController;

public class RefreshExpandableListViewContainer extends RefreshContainer<ExpandableListView> {

    private static RefreshLoadController defaultLoadController;

    public RefreshExpandableListViewContainer(Context context) {
        super(context);
        setRefreshLoadController(defaultLoadController);
    }

    public RefreshExpandableListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshLoadController(defaultLoadController);
    }

    public static void setDefaultRefreshLoadController(RefreshLoadController defaultLoadController) {
        RefreshExpandableListViewContainer.defaultLoadController = defaultLoadController;
    }

    @Override
    protected void findAndInitRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ExpandableListView) {
                setRefreshableView((ExpandableListView) child);
                return;
            }
        }
        setRefreshableView(new ExpandableListView(getContext()));
    }

    @Override
    public void setRefreshableView(ExpandableListView view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshAbsListViewController(view));
    }

    public void setAdapter(BaseExpandableListAdapter adapter) {
        if (adapter == null) {
            refreshableView.setAdapter((ExpandableListAdapter) null);
        } else {
            if (loadController == null) {
                throw new RuntimeException("unset RefreshLoadController");
            } else {
                RefreshExpandableListViewAdapter refreshExpandableListViewAdapter = new RefreshExpandableListViewAdapter(loadController, this, adapter);
                refreshableView.setAdapter(refreshExpandableListViewAdapter);
            }
        }
    }
}
