package com.rdb.refresh.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.rdb.refresh.RefreshBaseAdapter;
import com.rdb.refresh.RefreshListConfig;
import com.rdb.refresh.RefreshListViewProxy;
import com.rdb.refresh.RefreshTaskRequest;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class RefreshListPageActivity extends AppCompatActivity {

    private int count;
    private RefreshListViewProxy<String> listViewProxy;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
        RefreshListConfig refreshListConfig = new RefreshListConfig(RefreshLayout.BOTH);//不指定界面布局 自动创建无样式控件
//        RefreshListConfig refreshListConfig=new RefreshListConfig(RefreshLayout.BOTH,R.layout.activity_list0_layout,R.id.refreshContainer);//指定界面布局和刷新控件 不指定子控件 自动创建无样式子控件
//        RefreshListConfig refreshListConfig=new RefreshListConfig(RefreshLayout.BOTH,R.layout.activity_list1_layout,R.id.refreshContainer,R.id.emptyView);//指定界面布局 刷新控件
//        RefreshListConfig refreshListConfig=new RefreshListConfig(RefreshLayout.BOTH,R.layout.activity_list2_layout,R.id.refreshContainer,R.id.emptyView);//指定界面布局 刷新控件 空界面id
//        RefreshListConfig refreshListConfig=new RefreshListConfig(RefreshLayout.BOTH,R.layout.activity_list3_layout,R.id.refreshContainer,R.id.viewStub);//指定界面布局 刷新控件 空界面viewStub id
        listViewProxy = new RefreshListViewProxy<String>(this, refreshListConfig, taskRequest, new Adapter());
        //设置界面
        setContentView(listViewProxy.getView());
        ListView listView = listViewProxy.getRefreshableView();
        listView.setDivider(null);
        getSupportActionBar().setTitle("ListView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        listViewProxy.startRefreshingDelay(1000);
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
