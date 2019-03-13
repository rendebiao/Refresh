package com.rdb.refresh.list;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class RefreshListAdapter extends BaseAdapter {

    private static final int LOAD_TYPE = Integer.MIN_VALUE;

    private boolean showLoad;
    private BaseAdapter adapter;
    private DataSetObserver dataObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            notifyDataSetInvalidated();
        }
    };

    public RefreshListAdapter(BaseAdapter adapter) {
        setAdapter(adapter);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        adapter.registerDataSetObserver(dataObserver);
        notifyDataSetChanged();
    }

    protected void setShowLoad(boolean showLoad) {
        if (this.showLoad != showLoad) {
            this.showLoad = showLoad;
            notifyDataSetChanged();
        }
    }

    private boolean isLoadItem(int position) {
        return showLoad && position == getCount() - 1;
    }

    @Override
    public int getCount() {
        return adapter.getCount() + (showLoad ? 1 : 0);
    }

    @Override
    public Object getItem(int position) {
        return isLoadItem(position) ? null : adapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return isLoadItem(position) ? LOAD_TYPE : adapter.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadItem(position)) {
            return LOAD_TYPE;
        }
        return adapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isLoadItem(position)) {
            if (convertView == null) {
                convertView = onCreateLoadItemView(parent);
            }
            onBindLoadItemView(convertView);
            return convertView;
        }
        return adapter.getView(position, convertView, parent);
    }

    protected abstract View onCreateLoadItemView(ViewGroup viewGroup);

    protected abstract void onBindLoadItemView(View loadItemView);
}
