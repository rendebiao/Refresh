package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.rdb.refresh.RefreshContainer;

public class RefreshViewContainer extends RefreshContainer<View> {

    public RefreshViewContainer(Context context) {
        super(context);
    }

    public RefreshViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void findAndInitRefreshableView() {
        int count = getChildCount();
        if (count > 1) {
            View view = getChildAt(1);
            setRefreshableView(view);
        }
    }

    @Override
    public void setRefreshableView(View view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshViewController(view));
    }
}