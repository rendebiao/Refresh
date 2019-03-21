package com.rdb.refresh.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdb.refresh.Refresh;
import com.rdb.refresh.demo.R;
import com.rdb.refresh.paging.BaseExpandableListAdapter;
import com.rdb.refresh.paging.Config;
import com.rdb.refresh.paging.PagingExpandableList;
import com.rdb.refresh.paging.Request;
import com.rdb.refresh.view.RefreshLayout;

import java.util.ArrayList;

public class ExpandableListPageActivity extends AppCompatActivity {

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
    private PagingExpandableList<String> pagingExpandableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建
        Config config = new Config(RefreshLayout.BOTH, R.layout.view_empty_layout);//不指定界面布局 自动创建无样式控件
        config.setPageInfo(1, 10);
//        pagingExpandableList = new PagingExpandableList<String>(this, config, taskRequest, new Adapter());
        pagingExpandableList = Refresh.newExpandableList(this, config, taskRequest, new Adapter());
        //设置界面
        setContentView(pagingExpandableList.getView());
        getSupportActionBar().setTitle("ExpandableListView 框架示例");
        getSupportActionBar().setElevation(0);

        //请求数据
        pagingExpandableList.startRefreshingDelay(500);
    }

    class Adapter extends BaseExpandableListAdapter<String> {

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
