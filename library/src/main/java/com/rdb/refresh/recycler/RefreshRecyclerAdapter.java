package com.rdb.refresh.recycler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdb.refresh.LoadController;

public abstract class RefreshRecyclerAdapter extends RecyclerView.Adapter<RefreshRecyclerAdapter.RefreshItemHolder> {

    private static final int LOAD_TYPE = Integer.MIN_VALUE;

    private boolean showLoad;
    private LayoutInflater inflater;
    private RecyclerView.Adapter adapter;
    private LoadController loadController;
    private RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            notifyItemMoved(fromPosition, toPosition);
        }
    };

    public RefreshRecyclerAdapter(RecyclerView.Adapter adapter) {
        setAdapter(adapter);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        adapter.registerAdapterDataObserver(dataObserver);
        notifyDataSetChanged();
    }

    protected void setShowLoad(boolean showLoad) {
        if (this.showLoad != showLoad) {
            this.showLoad = showLoad;
            if (showLoad) {
                notifyItemInserted(adapter.getItemCount());
            } else {
                notifyItemRemoved(adapter.getItemCount());
            }
        }
    }

    private boolean isLoadItem(int position) {
        return showLoad && position == getItemCount() - 1;
    }

    @NonNull
    @Override
    public RefreshItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == LOAD_TYPE) {
            return onCreateLoadHolder(viewGroup);
        }
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(viewGroup, viewType);
        return new RefreshItemHolder(viewHolder.itemView, viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RefreshItemHolder refreshItemHolder, int position) {
        if (isLoadItem(position)) {
            onBindLoadHolder(refreshItemHolder);
        } else {
            adapter.onBindViewHolder(refreshItemHolder.viewHolder, position);
        }
    }

    protected abstract RefreshItemHolder onCreateLoadHolder(ViewGroup viewGroup);

    protected abstract void onBindLoadHolder(RefreshItemHolder refreshItemHolder);

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + (showLoad ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadItem(position)) {
            return LOAD_TYPE;
        }
        return adapter.getItemViewType(position);
    }

    public static class RefreshItemHolder extends RecyclerView.ViewHolder {

        private RecyclerView.ViewHolder viewHolder;

        public RefreshItemHolder(View itemView, RecyclerView.ViewHolder viewHolder) {
            super(itemView);
            this.viewHolder = viewHolder;
        }
    }
}
