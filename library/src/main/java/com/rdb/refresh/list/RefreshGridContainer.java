package com.rdb.refresh.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.rdb.refresh.RefreshContainer;

public class RefreshGridContainer extends RefreshContainer<GridView> {

    private GridView gridView;

    public RefreshGridContainer(Context context) {
        super(context);
    }

    public RefreshGridContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected GridView createRefreshableView(Context context) {
        gridView = new GridView(context);
        setRefreshController(new RefreshListController(gridView));
        return gridView;
    }
}