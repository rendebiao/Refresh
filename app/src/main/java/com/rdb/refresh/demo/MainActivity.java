package com.rdb.refresh.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdb.refresh.Refresh;
import com.rdb.refresh.view.LoadController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
        findViewById(R.id.viewButton).setOnClickListener(this);
        findViewById(R.id.scrollButton).setOnClickListener(this);
        findViewById(R.id.listButton1).setOnClickListener(this);
        findViewById(R.id.listButton2).setOnClickListener(this);
        findViewById(R.id.gridButton1).setOnClickListener(this);
        findViewById(R.id.gridButton2).setOnClickListener(this);
        findViewById(R.id.recyclerButton1).setOnClickListener(this);
        findViewById(R.id.recyclerButton2).setOnClickListener(this);
        findViewById(R.id.expandableButton1).setOnClickListener(this);
        findViewById(R.id.expandableButton2).setOnClickListener(this);
        LoadController defaultControoler = new LoadController(R.layout.item_load_layout) {

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
        } else if (v.getId() == R.id.listButton1) {
            Intent intent = new Intent(this, RefreshListActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.listButton2) {
            Intent intent = new Intent(this, RefreshListPageActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.gridButton1) {
            Intent intent = new Intent(this, RefreshGridActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.gridButton2) {
            Intent intent = new Intent(this, RefreshGridPageActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.recyclerButton1) {
            Intent intent = new Intent(this, RefreshRecyclerActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.recyclerButton2) {
            Intent intent = new Intent(this, RefreshRecyclerPageActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.expandableButton1) {
            Intent intent = new Intent(this, RefreshExpandableListActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.expandableButton2) {
            Intent intent = new Intent(this, RefreshExpandableListPageActivity.class);
            startActivity(intent);
        }
    }
}
