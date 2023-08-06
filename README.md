Refresh

刷新框架

    控件使用
        ViewContainer
        GridContainer
        ListContainer
        ExpListContainer
        RecyclerContainer

        <com.rdb.refresh.view.ListContainer
            android:id="@+id/refreshContainer"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
        refreshContainer = findViewById(R.id.refreshContainer);
        refreshContainer.setMode(RefreshLayout.BOTH);
        refreshContainer.setAdapter(adapter);
        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //...
            }
        });
        refreshContainer.setOnLoadListener(new RefreshContainer.OnLoadListener() {
            @Override
            public void onLoad() {
                //...
            }
        });
        refreshContainer.startRefreshingDelay(500, true);

    分页框架使用
        PagingList
        PagingGrid
        PagingRecycler
        PagingExpList
        
        Config config = new Config(RefreshLayout.BOTH, R.layout.activity_list4_layout, R.id.refreshContainer, R.id.viewStub, R.layout.view_empty_layout);//指定界面布局 刷新控件 viewStub id  空界面布局
        config.setPageInfo(1, 10);
        PagingList pagingList = Refresh.newList(this, config, taskRequest, new Adapter());
        //设置界面
        setContentView(pagingList.getView());
        pagingList.startRefreshingDelay(500);

[![](https://www.jitpack.io/v/rendebiao/Refresh.svg)](https://www.jitpack.io/#rendebiao/Refresh)
