package com.rdb.refresh.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.rdb.refresh.RefreshContainer;

public class RefreshListContainer extends RefreshContainer<ListView> {

    private ListView listView;
    private RefreshListAdapter refreshListAdapter;

    public RefreshListContainer(Context context) {
        super(context);
    }

    public RefreshListContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ListView createRefreshableView(Context context) {
        listView = new ListView(context);
        setRefreshController(new RefreshListController(listView));
        return listView;
    }

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            listView.setAdapter(null);
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
            listView.setAdapter(refreshListAdapter);
        }
    }
}