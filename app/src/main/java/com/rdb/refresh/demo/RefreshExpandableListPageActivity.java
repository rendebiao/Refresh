package com.rdb.refresh.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdb.refresh.RefreshBaseExpandableListAdapter;
import com.rdb.refresh.RefreshExpandableListViewProxy;
import com.rdb.refresh.RefreshListConfig;
import com.rdb.refresh.RefreshTaskRequest;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class RefreshExpandableListPageActivity extends AppCompatActivity {

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
    private RefreshExpandableListViewProxy<String> expandableListViewProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
        RefreshListConfig refreshListConfig = new RefreshListConfig(RefreshLayout.BOTH);//不指定界面布局 自动创建无样式控件
        expandableListViewProxy = new RefreshExpandableListViewProxy<String>(this, refreshListConfig, taskRequest, new Adapter());
        //设置界面
        setContentView(expandableListViewProxy.getView());
        getSupportActionBar().setTitle("ExpandableListView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        expandableListViewProxy.startRefreshingDelay(1000);
    }

    class Adapter extends RefreshBaseExpandableListAdapter<String> {

        private LayoutInflater inflater;

        public Adapter() {
            this.inflater = getLayoutInflater();
        }

        @Override
        public View onCreateGroupView(ViewGroup parent, int viewType) {
            return inflater.inflate(R.layout.item_text_layout, parent, false);
        }

        @Override
        public void onUpdateGroupView(View convertView, int groupPosition, boolean isExpanded) {
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText("---Group" + groupPosition + "---");
        }

        @Override
        public View onCreateChildView(ViewGroup parent, int viewType) {
            return inflater.inflate(R.layout.item_text_layout, parent, false);
        }

        @Override
        public void onUpdateChildView(View convertView, int groupPosition, int childPosition, boolean isLastChild) {
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText("---Child" + childPosition + "---");
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 5;
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return String.valueOf(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
