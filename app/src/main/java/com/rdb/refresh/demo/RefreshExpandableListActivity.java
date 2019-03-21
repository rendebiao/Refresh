package com.rdb.refresh.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.rdb.refresh.view.Container;
import com.rdb.refresh.view.ExpandableListViewContainer;
import com.rdb.refresh.view.RefreshLayout;

public class RefreshExpandableListActivity extends AppCompatActivity {


    private int count;
    private Adapter adapter;
    private ExpandableListViewContainer refreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_layout);
        getSupportActionBar().setTitle("ExpandableListView");
        getSupportActionBar().setElevation(0);
        refreshContainer = findViewById(R.id.refreshContainer);
        adapter = new Adapter(getLayoutInflater());
        refreshContainer.setMode(RefreshLayout.BOTH);
        refreshContainer.setAdapter(adapter);
        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                load();
            }
        });
        refreshContainer.getRefreshableView().setGroupIndicator(null);
        refreshContainer.setOnLoadListener(new Container.OnLoadListener() {
            @Override
            public void onLoad() {
                load();
            }
        });
        refreshContainer.startRefreshingDelay(500, true);
    }

    private void load() {
        refreshContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                count += 10;
                adapter.notifyDataSetChanged();
                refreshContainer.notifyComplete(count <= 100);
            }
        }, 1000);
    }

    class Adapter extends BaseExpandableListAdapter {

        private LayoutInflater inflater;

        public Adapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public int getGroupCount() {
            return count;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 5;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
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
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_text_layout, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText("---Group" + groupPosition + "---");
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_text_layout, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText("+++Child" + childPosition + "++++");
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
