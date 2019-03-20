package com.rdb.refresh.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdb.refresh.Refresh;
import com.rdb.refresh.view.RefreshLoadController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        findViewById(R.id.viewButton).setOnClickListener(this);
        findViewById(R.id.scrollButton).setOnClickListener(this);
        findViewById(R.id.listButton).setOnClickListener(this);
        findViewById(R.id.gridButton).setOnClickListener(this);
        findViewById(R.id.recyclerButton).setOnClickListener(this);
        findViewById(R.id.expandableButton).setOnClickListener(this);
        RefreshLoadController defaultControoler = new RefreshLoadController(R.layout.item_load_layout) {

            @Override
            public void updateLoadView(View view, boolean loading) {
                TextView loadView = view.findViewById(R.id.loadView);
                ProgressBar progressBar = view.findViewById(R.id.progressBar);
                loadView.setText(loading ? "正在加载" : "上拉加载更多");
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            }
        };
        //设置默认控制器 上拉加载
        Refresh.setDefaultLoadController(defaultControoler);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.viewButton) {
            Intent intent = new Intent(this, ViewActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.scrollButton) {
            Intent intent = new Intent(this, ScrollActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.listButton) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.gridButton) {
            Intent intent = new Intent(this, GridActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.recyclerButton) {
            Intent intent = new Intent(this, RecyclerActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.expandableButton) {
            Intent intent = new Intent(this, ExpandableListActivity.class);
            startActivity(intent);
        }
    }
}
