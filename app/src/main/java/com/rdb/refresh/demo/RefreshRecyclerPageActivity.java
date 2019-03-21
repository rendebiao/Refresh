package com.rdb.refresh.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdb.refresh.RefreshBaseRecyclerAdapter;
import com.rdb.refresh.RefreshListConfig;
import com.rdb.refresh.RefreshRecyclerViewProxy;
import com.rdb.refresh.RefreshTaskRequest;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class RefreshRecyclerPageActivity extends AppCompatActivity {

    private int count;
    RefreshTaskRequest taskRequest = new RefreshTaskRequest() {
        @Override
        protected ArrayList doInBackground(Context context, int page, int rowCount) {
            if (page == 1) {
                count = 0;
            }
            SystemClock.sleep(1000);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(String.valueOf(i + count));
            }
            count += 10;
            return list;
        }
    };
    private RefreshRecyclerViewProxy<String> recyclerViewProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
        RefreshListConfig refreshListConfig = new RefreshListConfig(RefreshLayout.BOTH);//不指定界面布局 自动创建无样式控件
        recyclerViewProxy = new RefreshRecyclerViewProxy<String>(this, refreshListConfig, taskRequest, new Adapter(getLayoutInflater()));
        //设置界面
        setContentView(recyclerViewProxy.getView());
        RecyclerView recyclerView = recyclerViewProxy.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("RecyclerView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        recyclerViewProxy.startRefreshingDelay(1000);
    }

    class Adapter extends RefreshBaseRecyclerAdapter<String> {

        private LayoutInflater inflater;

        public Adapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public View onCreateItemView(ViewGroup parent, int viewType) {
            return inflater.inflate(R.layout.item_text_layout, parent, false);
        }

        @Override
        public void onUpdateItemView(View convertView, int position) {
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText("---" + getItem(position) + "---");
        }
    }
}
