package com.rdb.refresh.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewController extends RefreshController<RecyclerView> {

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (container != null && container.autoLoad() && container.isHasMore() && container.isBottomEnable() && !container.isRefreshing() && !container.isLoading() && !isScrollTop() && isReadyForLoading()) {
                container.startLoading();
            }
        }
    };

    public RecyclerViewController() {
        super();
    }

    @Override
    protected void onRefreshableViewChanged(RecyclerView oldView, RecyclerView newView) {
        super.onRefreshableViewChanged(oldView, newView);
        if (oldView != null) {
            oldView.removeOnScrollListener(scrollListener);
        }
        if (newView != null) {
            newView.addOnScrollListener(scrollListener);
        }
    }

    @Override
    protected final boolean supportLoad() {
        return true;
    }

    @Override
    protected boolean canChildScrollUp() {
        return !isScrollTop();
    }

    @Override
    protected boolean isReadyForLoading() {
        if (refreshableView != null && refreshableView.getAdapter() != null && refreshableView.getAdapter().getItemCount() > 0) {
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition == (refreshableView.getAdapter().getItemCount() - 1)) {
                View item = refreshableView.getChildAt(refreshableView.getChildCount() - 1);
                if (item != null) {
                    int[] itemLocation = new int[2];
                    int[] recyclerLocation = new int[2];
                    item.getLocationOnScreen(itemLocation);
                    refreshableView.getLocationOnScreen(recyclerLocation);
                    return itemLocation[1] < recyclerLocation[1] + refreshableView.getHeight() - refreshableView.getPaddingBottom();
                }
            }
        }
        return false;
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        RecyclerView.Adapter adapter = refreshableView.getAdapter();
        if (adapter instanceof RecyclerViewWrapperAdapter) {
            ((RecyclerViewWrapperAdapter) adapter).setShowLoad(hasMore);
        }
    }

    @Override
    protected void onLoadingChanged(boolean loading) {
        RecyclerView.Adapter adapter = refreshableView.getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
        }
    }

    private boolean isScrollTop() {
        if (refreshableView.getChildCount() > 0) {
            return getFirstVisiblePosition() == 0
                    && refreshableView.getChildAt(0).getTop() == refreshableView.getPaddingTop();
        }
        return true;
    }

    public final int getFirstVisiblePosition() {
        View firstVisibleChild = refreshableView.getChildAt(0);
        return firstVisibleChild != null ? refreshableView.getChildAdapterPosition(firstVisibleChild) : -1;
    }

    public final int getLastVisiblePosition() {
        View lastVisibleChild = refreshableView.getChildAt(refreshableView.getChildCount() - 1);
        return lastVisibleChild != null ? refreshableView.getChildAdapterPosition(lastVisibleChild) : -1;
    }
}
