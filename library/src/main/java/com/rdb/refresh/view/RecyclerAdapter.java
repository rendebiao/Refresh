package com.rdb.refresh.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RefreshItemHolder> {

    private static final int LOAD_TYPE_ID = Integer.MIN_VALUE;

    private boolean showLoad;
    private RecyclerView.Adapter adapter;
    private LoadController loadController;
    private RecyclerContainer recyclerContainer;
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

    public RecyclerAdapter(LoadController loadController, RecyclerContainer recyclerContainer, RecyclerView.Adapter adapter) {
        this.loadController = loadController;
        this.recyclerContainer = recyclerContainer;
        this.showLoad = recyclerContainer.isShowNoMore();
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
        if (viewType == LOAD_TYPE_ID) {
            View view = LayoutInflater.from(recyclerContainer.getContext()).inflate(loadController.getLoadLayout(), viewGroup, false);
            loadController.initLoadView(view);
            return new RefreshItemHolder(view, null);
        } else {
            RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(viewGroup, viewType);
            return new RefreshItemHolder(viewHolder.itemView, viewHolder);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RefreshItemHolder refreshItemHolder, int position) {
        if (isLoadItem(position)) {
            loadController.updateLoadView(refreshItemHolder.itemView, recyclerContainer.isLoading(), recyclerContainer.isHasMore());
        } else {
            adapter.onBindViewHolder(refreshItemHolder.viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        int count = adapter.getItemCount();
        return count == 0 ? 0 : count + (showLoad ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadItem(position)) {
            return LOAD_TYPE_ID;
        } else {
            return adapter.getItemViewType(position);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RefreshItemHolder holder, int position, @NonNull List<Object> payloads) {
        if (isLoadItem(position)) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            adapter.onBindViewHolder(holder.viewHolder, position, payloads);
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        adapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        if (isLoadItem(position)) {
            return LOAD_TYPE_ID;
        } else {
            return super.getItemId(position);
        }
    }

    @Override
    public void onViewRecycled(@NonNull RefreshItemHolder holder) {
        if (holder.viewHolder == null) {
            super.onViewRecycled(holder);
        } else {
            adapter.onViewRecycled(holder.viewHolder);
        }
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RefreshItemHolder holder) {
        if (holder.viewHolder == null) {
            return super.onFailedToRecycleView(holder);
        } else {
            return adapter.onFailedToRecycleView(holder.viewHolder);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RefreshItemHolder holder) {
        if (holder.viewHolder == null) {
            super.onViewAttachedToWindow(holder);
        } else {
            adapter.onViewAttachedToWindow(holder.viewHolder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RefreshItemHolder holder) {
        if (holder.viewHolder == null) {
            super.onViewDetachedFromWindow(holder);
        } else {
            adapter.onViewDetachedFromWindow(holder.viewHolder);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter.onDetachedFromRecyclerView(recyclerView);
    }

    public static class RefreshItemHolder extends RecyclerView.ViewHolder {

        private RecyclerView.ViewHolder viewHolder;

        public RefreshItemHolder(View itemView, RecyclerView.ViewHolder viewHolder) {
            super(itemView);
            this.viewHolder = viewHolder;
        }
    }
}
