package com.rdb.refresh.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdb.refresh.LoadController;
import com.rdb.refresh.RefreshContainer;
import com.rdb.refresh.RefreshMode;
import com.rdb.refresh.recycler.RefreshRecyclerContainer;

public class MainActivity extends AppCompatActivity {

    private int count;
    private Adapter adapter;
    private RefreshRecyclerContainer refreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshContainer = findViewById(R.id.refreshContainer);
        adapter = new Adapter(getLayoutInflater());
        refreshContainer.setMode(RefreshMode.BOTH);
        refreshContainer.setLoadController(new LoadController() {
            @Override
            public int getLoadLayout() {
                return R.layout.item_load_layout;
            }

            @Override
            public void updateLoadView(View view, boolean loading) {
                TextView loadView = view.findViewById(R.id.loadView);
                ProgressBar progressBar = view.findViewById(R.id.progressBar);
                loadView.setText(loading ? "正在加载" : "上拉加载更多");
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            }
        });
        refreshContainer.setLayoutManager(new LinearLayoutManager(this));
        refreshContainer.setAdapter(adapter);
        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                load();
            }
        });
        refreshContainer.setOnLoadListener(new RefreshContainer.OnLoadListener() {
            @Override
            public void onLoad() {
                load();
            }
        });
        refreshContainer.startRefreshing(true);
    }

    private void load() {
        refreshContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                count += 10;
                adapter.notifyDataSetChanged();
                refreshContainer.notifyComplete(count <= 30);
            }
        }, 1000);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater inflater;

        public Adapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(inflater.inflate(R.layout.item_text_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            TextView textView = viewHolder.itemView.findViewById(R.id.textView);
            textView.setText("---" + i + "---");
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
