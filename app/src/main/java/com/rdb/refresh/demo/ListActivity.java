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
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdb.refresh.LoadController;
import com.rdb.refresh.RefreshContainer;
import com.rdb.refresh.RefreshMode;
import com.rdb.refresh.list.RefreshGridContainer;
import com.rdb.refresh.list.RefreshListContainer;
import com.rdb.refresh.recycler.RefreshRecyclerContainer;

public class ListActivity extends AppCompatActivity {


    private int count;
    private BaseAdapter adapter;
    private RefreshListContainer refreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        getSupportActionBar().setTitle("ListView");
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
            if(convertView==null){
                convertView=inflater.inflate(R.layout.item_text_layout,parent,false);
            }
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText("---" + position + "---");
            return convertView;
        }
    }
}
