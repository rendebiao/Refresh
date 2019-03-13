package com.rdb.refresh.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.rdb.refresh.RefreshContainer;

public class RefreshGridContainer extends RefreshContainer<GridView> {

    private RefreshListAdapter refreshListAdapter;

    public RefreshGridContainer(Context context) {
        super(context);
    }

    public RefreshGridContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof GridView) {
                setRefreshableView((GridView) child);
                break;
            }
        }
    }

    @Override
    public void setRefreshableView(GridView view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshListController(view));
    }

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            refreshableView.setAdapter(null);
        } else {
            refreshListAdapter = new RefreshListAdapter(adapter) {
                @Override
                protected View onCreateLoadItemView(ViewGroup viewGroup) {
                    return LayoutInflater.from(getContext()).inflate(loadController.getLoadLayout(), viewGroup, false);
                }

                @Override
                protected void onBindLoadItemView(View loadItemView) {
                    loadController.updateLoadView(loadItemView, isLoading());
                }
            };
            refreshableView.setAdapter(refreshListAdapter);
        }
    }
}