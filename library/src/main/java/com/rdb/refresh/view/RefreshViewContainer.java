package com.rdb.refresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class RefreshViewContainer extends RefreshContainer<View> {

    public RefreshViewContainer(Context context) {
        super(context);
    }

    public RefreshViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View findRefreshableView() {
        int count = getChildCount();
        if (count > 1) {
            return getChildAt(1);
        }
        return null;
    }


    @Override
    protected View createRefreshableView() {
        return null;
    }

    @Override
    public void setRefreshableView(View view) {
        super.setRefreshableView(view);
        setRefreshController(new RefreshViewController(view));
    }
}