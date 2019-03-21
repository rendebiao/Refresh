package com.rdb.refresh.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.rdb.refresh.view.RefreshLayout;
import com.rdb.refresh.view.ViewContainer;

public class ScrollActivity extends AppCompatActivity {

    private ViewContainer refreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_layout);
        getSupportActionBar().setTitle("ScrollView");
        getSupportActionBar().setElevation(0);
        refreshContainer = findViewById(R.id.refreshContainer);
        refreshContainer.setMode(RefreshLayout.TOP);
        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        refreshContainer.startRefreshing(true);
    }

    private void load() {
        refreshContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshContainer.notifyComplete();
            }
        }, 1000);
    }
}
