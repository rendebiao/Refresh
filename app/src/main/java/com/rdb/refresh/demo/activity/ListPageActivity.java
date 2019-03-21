package com.rdb.refresh.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.rdb.refresh.Refresh;
import com.rdb.refresh.demo.R;
import com.rdb.refresh.paging.BaseAdapter;
import com.rdb.refresh.paging.Config;
import com.rdb.refresh.paging.PagingList;
import com.rdb.refresh.paging.Request;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class ListPageActivity extends AppCompatActivity {

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
    private PagingList<String> pagingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
//        Config config = new Config(RefreshLayout.BOTH);//不指定界面布局 自动创建无样式控件
//        Config config = new Config(RefreshLayout.BOTH, R.layout.view_empty_layout);//不指定界面布局 自动创建无样式控件 指定空界面
//        Config config = new Config(RefreshLayout.BOTH,R.layout.activity_list0_layout,R.id.refreshContainer);//指定界面布局 刷新控件 自动创建无样式子控件
//        Config config = new Config(RefreshLayout.BOTH,R.layout.activity_list1_layout,R.id.refreshContainer);//指定界面布局 刷新控件 子控件
//        Config config = new Config(RefreshLayout.BOTH,R.layout.activity_list2_layout,R.id.refreshContainer,R.id.emptyView);//指定界面布局 刷新控件 空界面id
//        Config config = new Config(RefreshLayout.BOTH,R.layout.activity_list3_layout,R.id.refreshContainer,R.id.viewStub);//指定界面布局 刷新控件 子控件 空界面viewStub id
        Config config = new Config(RefreshLayout.BOTH, R.layout.activity_list4_layout, R.id.refreshContainer, R.id.viewStub, R.layout.view_empty_layout);//指定界面布局 刷新控件 viewStub id  空界面布局
        config.setPageInfo(1, 10);
//        pagingList = new PagingList<String>(this, config, taskRequest, new Adapter());
        pagingList = Refresh.newList(this, config, taskRequest, new Adapter());
        //设置界面
        setContentView(pagingList.getView());
        ListView listView = pagingList.getRefreshableView();
        listView.setDivider(null);
        getSupportActionBar().setTitle("ListView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        pagingList.startRefreshingDelay(500);
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
