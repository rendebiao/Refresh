package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.rdb.refresh.Refresh;

public class RefreshExpandableListViewContainer extends RefreshContainer<ExpandableListView> {

    public RefreshExpandableListViewContainer(Context context) {
        super(context);
        setRefreshLoadController(Refresh.getExpandableListLoadController());
    }

    public RefreshExpandableListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRefreshLoadController(Refresh.getExpandableListLoadController());
    }

    public RefreshExpandableListViewContainer(Context context, ExpandableListView refreshableView) {
        super(context, refreshableView);
        setRefreshLoadController(Refresh.getExpandableListLoadController());
    }

    @Override
    protected ExpandableListView findRefreshableView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ExpandableListView) {
                return (ExpandableListView) child;
            }
        }
        return null;
    }

    @Override
    protected ExpandableListView createRefreshableView() {
        return new ExpandableListView(getContext());
    }

    @Override
    public void setRefreshableView(ExpandableListView view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshExpandableListViewController(view));
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
