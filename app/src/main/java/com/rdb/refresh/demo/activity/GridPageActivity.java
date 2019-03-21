package com.rdb.refresh.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rdb.refresh.demo.R;
import com.rdb.refresh.paging.BaseAdapter;
import com.rdb.refresh.paging.Config;
import com.rdb.refresh.paging.PagingGrid;
import com.rdb.refresh.paging.Request;
import com.rdb.refresh.view.LoadController;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class GridPageActivity extends AppCompatActivity {

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
    private PagingGrid<String> pagingGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
        Config config = new Config(RefreshLayout.BOTH, R.layout.view_empty_layout);//不指定界面布局 自动创建无样式控件
        config.setPageInfo(1, 20);
        pagingGrid = new PagingGrid<String>(this, config, taskRequest, new Adapter());
        pagingGrid.getRefreshableView().setNumColumns(3);
        //设置单独控制器 点击加载
        pagingGrid.getRefreshContainer().setRefreshLoadController(new LoadController(R.layout.item_load_layout) {

            @Override
            public boolean autoLoad() {
                return false;
            }

            @Override
            public boolean showNoMore() {
                return true;
            }

            @Override
            public void updateLoadView(View view, boolean loading, boolean hasMore) {
                TextView loadView = view.findViewById(R.id.loadView);
                ProgressBar progressBar = view.findViewById(R.id.progressBar);
                loadView.setText(loading ? "正在加载" : (hasMore ? "点击加载更多" : "没有更多"));
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
                if (!loading && hasMore) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pagingGrid.getRefreshContainer().startLoading();
                        }
                    });
                } else {
                    view.setOnClickListener(null);
                }
            }
        });
        //设置界面
        setContentView(pagingGrid.getView());
        getSupportActionBar().setTitle("GridView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        pagingGrid.startRefreshingDelay(500);
    }

    class Adapter extends BaseAdapter {

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
