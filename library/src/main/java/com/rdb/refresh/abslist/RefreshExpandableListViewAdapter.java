package com.rdb.refresh.abslist;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.rdb.refresh.RefreshLoadController;

class RefreshExpandableListViewAdapter extends BaseExpandableListAdapter {

    private static final int LOAD_TYPE_ID = Integer.MIN_VALUE;

    private boolean showLoad;
    private RefreshLoadController loadController;
    private BaseExpandableListAdapter adapter;
    private RefreshExpandableListViewContainer expandableListContainer;
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

    public RefreshExpandableListViewAdapter(RefreshLoadController loadController, RefreshExpandableListViewContainer expandableListContainer, BaseExpandableListAdapter adapter) {
        this.loadController = loadController;
        this.expandableListContainer = expandableListContainer;
        setAdapter(adapter);
    }

    public void setAdapter(BaseExpandableListAdapter adapter) {
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
        return showLoad && position == getGroupCount() - 1;
    }

    @Override
    public int getGroupCount() {
        return adapter.getGroupCount() + (showLoad ? 1 : 0);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return isLoadItem(groupPosition) ? 0 : adapter.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return isLoadItem(groupPosition) ? null : adapter.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return isLoadItem(groupPosition) ? null : adapter.getChild(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return isLoadItem(groupPosition) ? LOAD_TYPE_ID : adapter.getGroupId(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return adapter.getChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (isLoadItem(groupPosition)) {
            if (convertView == null) {
                convertView = LayoutInflater.from(expandableListContainer.getContext()).inflate(loadController.getLoadLayout(), parent, false);
                loadController.initLoadView(convertView);
            }
            loadController.updateLoadView(convertView, expandableListContainer.isLoading());
            return convertView;
        } else {
            return adapter.getGroupView(groupPosition, isExpanded, convertView, parent);
        }
    }

    @Override
    public int getGroupTypeCount() {
        return adapter.getGroupTypeCount() + 1;
    }

    @Override
    public int getGroupType(int groupPosition) {
        if (isLoadItem(groupPosition)) {
            return LOAD_TYPE_ID;
        }
        return adapter.getGroupType(groupPosition);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return adapter.areAllItemsEnabled();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        if (!isLoadItem(groupPosition)) {
            adapter.onGroupCollapsed(groupPosition);
        }
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        if (!isLoadItem(groupPosition)) {
            adapter.onGroupExpanded(groupPosition);
        }
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return adapter.getCombinedChildId(groupId, childId);
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        if (groupId == LOAD_TYPE_ID) {
            return LOAD_TYPE_ID;
        } else {
            return adapter.getCombinedGroupId(groupId);
        }
    }

    @Override
    public boolean isEmpty() {
        return adapter.isEmpty();
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return adapter.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return adapter.getChildTypeCount();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return adapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        if (isLoadItem(groupPosition)) {
            return false;
        } else {
            return adapter.isChildSelectable(groupPosition, childPosition);
        }
    }
}
