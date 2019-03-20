package com.rdb.refresh.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdb.refresh.view.RefreshContainer;
import com.rdb.refresh.view.RefreshGridViewContainer;
import com.rdb.refresh.view.RefreshLayout;
import com.rdb.refresh.view.RefreshLoadController;

public class GridActivity extends AppCompatActivity {

    private int count;
    private BaseAdapter adapter;
    private RefreshGridViewContainer refreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);
        getSupportActionBar().setTitle("GridView");
        getSupportActionBar().setElevation(0);
        refreshContainer = findViewById(R.id.refreshContainer);
        adapter = new Adapter(getLayoutInflater());
        refreshContainer.setMode(RefreshLayout.BOTH);
        //设置单独控制器 点击加载
        refreshContainer.setRefreshLoadController(new RefreshLoadController(R.layout.item_load_layout) {

            @Override
            public boolean autoLoad() {
                return false;
            }

            @Override
            public void updateLoadView(View view, boolean loading) {
                TextView loadView = view.findViewById(R.id.loadView);
                ProgressBar progressBar = view.findViewById(R.id.progressBar);
                loadView.setText(loading ? "正在加载" : "点击加载更多");
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
                if (!loading) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refreshContainer.startLoading();
                        }
                    });
                } else {
                    view.setOnClickListener(null);
                }
            }
        });
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
        refreshContainer.startRefreshingDelay(500, true);
    }

    private void load() {
        refreshContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                count += 10;
                adapter.notifyDataSetChanged();
                refreshContainer.notifyComplete(count <= 100);
            }
        }, 1000);
    }

    class Adapter extends BaseAdapter {

        private LayoutInflater inflater;

        public Adapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }


        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_text_layout, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText("---" + position + "---");
            return convertView;
        }
    }
}
