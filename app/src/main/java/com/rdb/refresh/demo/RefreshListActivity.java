package com.rdb.refresh.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rdb.refresh.view.Container;
import com.rdb.refresh.view.ListViewContainer;
import com.rdb.refresh.view.RefreshLayout;

public class RefreshListActivity extends AppCompatActivity {

    private int count;
    private BaseAdapter adapter;
    private ListViewContainer refreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list0_layout);
        getSupportActionBar().setTitle("ListView 控件示例");
        getSupportActionBar().setElevation(0);
        refreshContainer = findViewById(R.id.refreshContainer);
        adapter = new Adapter(getLayoutInflater());
        refreshContainer.setMode(RefreshLayout.BOTH);
        refreshContainer.setAdapter(adapter);
        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                load();
            }
        });
        refreshContainer.setOnLoadListener(new Container.OnLoadListener() {
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
