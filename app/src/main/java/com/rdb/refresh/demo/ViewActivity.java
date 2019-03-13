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
import com.rdb.refresh.view.RefreshViewContainer;

public class ViewActivity extends AppCompatActivity {

    private RefreshViewContainer refreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout);
        getSupportActionBar().setTitle("View");
        refreshContainer = findViewById(R.id.refreshContainer);
        refreshContainer.setMode(RefreshMode.TOP);
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
