package com.rdb.refresh.view;

import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

class AbsListAdapter extends BaseAdapter {

    private boolean showLoad;
    private BaseAdapter adapter;
    private LoadController loadController;
    private AbsListContainer listContainer;
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

    public AbsListAdapter(LoadController loadController, AbsListContainer listContainer, BaseAdapter adapter) {
        this.loadController = loadController;
        this.listContainer = listContainer;
        this.showLoad = listContainer.isShowNoMore();
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
        int count = adapter.getCount();
        return count == 0 ? 0 : count + (showLoad ? 1 : 0);
    }


    @Override
    public Object getItem(int position) {
        return isLoadItem(position) ? null : adapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return isLoadItem(position) ? Long.MIN_VALUE : adapter.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadItem(position)) {
            return 0;
        }
        return adapter.getItemViewType(position) + 1;
    }

    @Override
    public int getViewTypeCount() {
        return adapter.getViewTypeCount() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isLoadItem(position)) {
            if (convertView == null) {
                convertView = LayoutInflater.from(listContainer.getContext()).inflate(loadController.getLoadLayout(), parent, false);
                loadController.initLoadView(convertView);
            }
            loadController.updateLoadView(convertView, listContainer.isLoading(), listContainer.isHasMore());
            return convertView;
        } else {
            return adapter.getView(position, convertView, parent);
        }
    }


    @Override
    public boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return adapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        if (isLoadItem(position)) {
            return true;
        } else {
            return super.isEnabled(position);
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (isLoadItem(position)) {
            return super.getDropDownView(position, convertView, parent);
        } else {
            return adapter.getDropDownView(position, convertView, parent);
        }
    }

    @Override
    public boolean isEmpty() {
        return adapter.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public CharSequence[] getAutofillOptions() {
        return adapter.getAutofillOptions();
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public void setAutofillOptions(@Nullable CharSequence... options) {
        adapter.setAutofillOptions(options);
    }
}
