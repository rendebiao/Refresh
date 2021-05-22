package com.rdb.refresh.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rdb.refresh.demo.R;
import com.rdb.refresh.paging.Config;
import com.rdb.refresh.paging.PagingRecycler;
import com.rdb.refresh.paging.RecyclerAdapter;
import com.rdb.refresh.paging.Request;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class PageActivity extends AppCompatActivity {

    private int count;
    Request.TaskRequest taskRequest = new Request.TaskRequest() {
        @Override
        protected ArrayList doInBackground(Context context, int page, int rowCount) {
            if (page == 1) {
                count = 0;
            }
            SystemClock.sleep(1000);
            ArrayList<String> list = new ArrayList<>();
            boolean hasData = System.currentTimeMillis() % 4 != 1;
            if (hasData) {
                for (int i = 0; i < rowCount; i++) {
                    list.add(String.valueOf(i + count));
                }
                count += rowCount;
            }
            return list;
        }
    };
    private PagingRecycler<String> pagingRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
        Config config = new Config(RefreshLayout.BOTH, R.layout.view_empty_layout);//不指定界面布局 自动创建无样式控件
        config.setPageInfo(1, 20);
        pagingRecycler = new PagingRecycler<String>(this, config, taskRequest, new Adapter(getLayoutInflater()));
        //设置界面
        setContentView(pagingRecycler.getView());
        RecyclerView recyclerView = pagingRecycler.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("RecyclerView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        pagingRecycler.startRefreshingDelay(500);
    }

    class Adapter extends RecyclerAdapter<String> {

        private final LayoutInflater inflater;

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
