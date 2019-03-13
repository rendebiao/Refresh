package com.rdb.refresh.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdb.refresh.RefreshController;

public class RefreshRecyclerController extends RefreshController {

    private RecyclerView recyclerView;

    public RefreshRecyclerController(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (container != null && container.isHasMore() && container.isBottomEnable() && !container.isRefreshing() && !container.isLoading() && !isScrollTop() && isReadyForLoading()) {
                    container.startLoading();
                }
            }
        });
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
        if (recyclerView != null && recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() > 0) {
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition == (recyclerView.getAdapter().getItemCount() - 1)) {
                View item = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                if (item != null) {
                    int[] itemLocation = new int[2];
                    int[] recyclerLocation = new int[2];
                    item.getLocationOnScreen(itemLocation);
                    recyclerView.getLocationOnScreen(recyclerLocation);
                    return itemLocation[1] + item.getHeight() * 0.5f < recyclerLocation[1] + recyclerView.getHeight() - recyclerView.getPaddingBottom();
                }
            }
        }
        return false;
    }

    @Override
    protected void onHasMoreChanged(boolean hasMore) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof RefreshRecyclerAdapter) {
            ((RefreshRecyclerAdapter) adapter).setShowLoad(hasMore);
        }
    }

    @Override
    protected void onLoadingChanged(boolean loading) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
        }
    }

    private boolean isScrollTop() {
        if (recyclerView.getChildCount() > 0) {
            return getFirstVisiblePosition() == 0
                    && recyclerView.getChildAt(0).getTop() == recyclerView.getPaddingTop();
        }
        return true;
    }

    public final int getFirstVisiblePosition() {
        View firstVisibleChild = recyclerView.getChildAt(0);
        return firstVisibleChild != null ? recyclerView.getChildAdapterPosition(firstVisibleChild) : -1;
    }

    public final int getLastVisiblePosition() {
        View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        return lastVisibleChild != null ? recyclerView.getChildAdapterPosition(lastVisibleChild) : -1;
    }
}
