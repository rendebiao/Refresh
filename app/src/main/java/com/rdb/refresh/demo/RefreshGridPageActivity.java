package com.rdb.refresh.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdb.refresh.RefreshBaseAdapter;
import com.rdb.refresh.RefreshGridViewProxy;
import com.rdb.refresh.RefreshListConfig;
import com.rdb.refresh.RefreshTaskRequest;
import com.rdb.refresh.view.LoadController;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class RefreshGridPageActivity extends AppCompatActivity {

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
    private RefreshGridViewProxy<String> gridViewProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
        RefreshListConfig refreshListConfig = new RefreshListConfig(RefreshLayout.BOTH);//不指定界面布局 自动创建无样式控件
        gridViewProxy = new RefreshGridViewProxy<String>(this, refreshListConfig, taskRequest, new Adapter());
        gridViewProxy.getRefreshableView().setNumColumns(3);
        //设置单独控制器 点击加载
        gridViewProxy.getRefreshContainer().setRefreshLoadController(new LoadController(R.layout.item_load_layout) {

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
                            gridViewProxy.getRefreshContainer().startLoading();
                        }
                    });
                } else {
                    view.setOnClickListener(null);
                }
            }
        });
        //设置界面
        setContentView(gridViewProxy.getView());
        getSupportActionBar().setTitle("GridView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        gridViewProxy.startRefreshingDelay(1000);
    }

    class Adapter extends RefreshBaseAdapter {

        private LayoutInflater inflater;

        public Adapter() {
            this.inflater = getLayoutInflater();
        }

        @Override
        public long getItemId(int position) {
            return 0;
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

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }
}
