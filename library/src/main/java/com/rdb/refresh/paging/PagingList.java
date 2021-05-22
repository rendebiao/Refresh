package com.rdb.refresh.paging;

import android.content.Context;
import android.widget.ListView;

import com.rdb.refresh.view.ListContainer;

public class PagingList<D> extends Paging<D, ListContainer> {

    protected ListView listView;
    protected BaseAdapter<D> adapter;

    public PagingList(Context context, Config config, Request request, BaseAdapter<D> adapter) {
        super(context, config, request);
        listView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected ListContainer createRefreshContainer(Context context) {
        return new ListContainer(context);
    }

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter != null && adapter.isEmpty();
    }

    public ListView getRefreshableView() {
        return listView;
    }

    public BaseAdapter<D> getAdapter() {
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

}
