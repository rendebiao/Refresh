package com.rdb.refresh.paging;

import android.content.Context;
import android.widget.ExpandableListView;

import com.rdb.refresh.view.ExpandableListContainer;

public class PagingExpandableList<D> extends Paging<D, ExpandableListContainer> {

    protected ExpandableListView listView;
    protected BaseExpandableListAdapter<D> adapter;

    public PagingExpandableList(Context context, Config config, Request request, BaseExpandableListAdapter<D> adapter) {
        super(context, config, request);
        listView = refreshContainer.getRefreshableView();
        adapter.setItems(getDataList());
        this.adapter = adapter;
        refreshContainer.setAdapter(adapter);
    }

    @Override
    protected ExpandableListContainer createRefreshContainer(Context context) {
        return new ExpandableListContainer(context);
    }

    public final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected final boolean isEmpty() {
        return adapter == null ? false : adapter.isEmpty();
    }

    public ExpandableListView getRefreshableView() {
        return listView;
    }

    public android.widget.BaseExpandableListAdapter getAdapter() {
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

    @Override
    public final void scrollToBottom() {
        if (listView != null) {
            listView.setSelection(Integer.MAX_VALUE);
        }
    }
}
