package com.rdb.refresh;

import android.content.Context;
import android.widget.ListView;

import com.rdb.refresh.view.RefreshListViewContainer;

import java.util.List;

public class RefreshListViewProxy<D> extends RefreshPageProxy<D, RefreshListViewContainer> {

    protected ListView listView;
    protected BaseAdapter adapter;

    public RefreshListViewProxy(Context context, RefreshListConfig refreshListConfig, RefreshRequest refreshRequest, BaseAdapter<D> adapter) {
        super(context, refreshListConfig, refreshRequest);
        listView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected RefreshListViewContainer createRefreshContainer(Context context) {
        return new RefreshListViewContainer(context);
    }

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

    @Override
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

    public static abstract class BaseAdapter<D> extends android.widget.BaseAdapter {

        private List<D> items;

        public BaseAdapter() {
        }

        private void setItems(List<D> items) {
            this.items = items;
        }

        @Override
        public final int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public final D getItem(int position) {
            return items.get(position);
        }
    }
}
