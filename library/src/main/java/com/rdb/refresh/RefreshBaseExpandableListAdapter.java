package com.rdb.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

public abstract class RefreshBaseExpandableListAdapter<D> extends BaseExpandableListAdapter {

    private List<D> items;

    public RefreshBaseExpandableListAdapter() {
    }

    void setItems(List<D> items) {
        this.items = items;
    }

    @Override
    public final int getGroupCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public final D getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public final View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int viewType = getGroupType(groupPosition);
        if (convertView == null) {
            convertView = onCreateGroupView(parent, viewType);
        }
        onUpdateGroupView(convertView, groupPosition, isExpanded);
        return convertView;
    }

    @Override
    public final View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        int viewType = getChildType(groupPosition, childPosition);
        if (convertView == null) {
            convertView = onCreateChildView(parent, viewType);
        }
        onUpdateChildView(convertView, groupPosition, childPosition, isLastChild);
        return convertView;
    }

    public abstract View onCreateGroupView(ViewGroup parent, int viewType);

    public abstract void onUpdateGroupView(View convertView, int groupPosition, boolean isExpanded);

    public abstract View onCreateChildView(ViewGroup parent, int viewType);

    public abstract void onUpdateChildView(View convertView, int groupPosition, int childPosition, boolean isLastChild);
}
