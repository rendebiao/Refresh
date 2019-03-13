package com.rdb.refresh.demo;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.viewButton).setOnClickListener(this);
        findViewById(R.id.scrollButton).setOnClickListener(this);
        findViewById(R.id.listButton).setOnClickListener(this);
        findViewById(R.id.gridButton).setOnClickListener(this);
        findViewById(R.id.recyclerButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.viewButton){
            Intent intent=new Intent(this,ViewActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.scrollButton){
            Intent intent=new Intent(this,ScrollActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.listButton){
            Intent intent=new Intent(this,ListActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.gridButton){
            Intent intent=new Intent(this,GridActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.recyclerButton){
            Intent intent=new Intent(this,RecyclerActivity.class);
            startActivity(intent);
        }
    }
}
