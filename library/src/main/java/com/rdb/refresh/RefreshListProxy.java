package com.rdb.refresh;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.rdb.refresh.abslist.RefreshListViewContainer;

import java.util.ArrayList;

public abstract class RefreshListProxy<D> extends RefreshPageProxy<D, RefreshListViewContainer> {

    protected ListView listView;
    protected BaseAdapter adapter;

    public RefreshListProxy(Context context) {
        super(context);
    }

    @Override
    public void onCreateView(View view) {
        super.onCreateView(view);
        listView = refreshContainer.getRefreshableView();
        adapter = createAdapter(context, getDataList());
        listView.setAdapter(adapter);
    }

    protected abstract BaseAdapter createAdapter(Context context, ArrayList<D> items);

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.isEmpty();
    }

    public ListView getListView() {
        return listView;
    }

    public BaseAdapter getAdapter() {
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
}
