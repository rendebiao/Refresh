package com.rdb.refresh;

import android.content.Context;

import com.rdb.refresh.paging.BaseAdapter;
import com.rdb.refresh.paging.Config;
import com.rdb.refresh.paging.ExpListAdapter;
import com.rdb.refresh.paging.PagingExpList;
import com.rdb.refresh.paging.PagingGrid;
import com.rdb.refresh.paging.PagingList;
import com.rdb.refresh.paging.PagingRecycler;
import com.rdb.refresh.paging.RecyclerAdapter;
import com.rdb.refresh.paging.Request;
import com.rdb.refresh.view.LoadController;

public class Refresh {

    private static int startPage = 1;
    private static int rowCount = 10;
    private static LoadController defaultLoadController;
    private static LoadController listLoadController;
    private static LoadController gridLoadController;
    private static LoadController recyclerLoadController;
    private static LoadController expandableListLoadController;

    public static int getStartPage() {
        return startPage;
    }

    public static int getRowCount() {
        return rowCount;
    }

    public static void setPageInfo(int startPage, int rowCount) {
        Refresh.startPage = startPage;
        Refresh.rowCount = rowCount;
    }

    public static void setDefaultLoadController(LoadController defaultLoadController) {
        Refresh.defaultLoadController = defaultLoadController;
    }

    public static LoadController getListLoadController() {
        return listLoadController == null ? defaultLoadController : listLoadController;
    }

    public static void setListLoadController(LoadController listLoadController) {
        Refresh.listLoadController = listLoadController;
    }

    public static LoadController getGridLoadController() {
        return gridLoadController == null ? defaultLoadController : gridLoadController;
    }

    public static void setGridLoadController(LoadController gridLoadController) {
        Refresh.gridLoadController = gridLoadController;
    }

    public static LoadController getRecyclerLoadController() {
        return recyclerLoadController == null ? defaultLoadController : recyclerLoadController;
    }

    public static void setRecyclerLoadController(LoadController recyclerLoadController) {
        Refresh.recyclerLoadController = recyclerLoadController;
    }

    public static LoadController getExpandableListLoadController() {
        return expandableListLoadController == null ? defaultLoadController : expandableListLoadController;
    }

    public static void setExpandableListLoadController(LoadController expandableListLoadController) {
        Refresh.expandableListLoadController = expandableListLoadController;
    }

    public static <D> PagingList<D> newList(Context context, Config config, Request request, BaseAdapter<D> adapter) {
        return new PagingList<>(context, config, request, adapter);
    }

    public static <D> PagingGrid<D> newGrid(Context context, Config config, Request request, BaseAdapter<D> adapter) {
        return new PagingGrid<>(context, config, request, adapter);
    }

    public static <D> PagingRecycler<D> newRecycler(Context context, Config config, Request request, RecyclerAdapter<D> adapter) {
        return new PagingRecycler<>(context, config, request, adapter);
    }

    public static <D> PagingExpList<D> newExpList(Context context, Config config, Request request, ExpListAdapter<D> adapter) {
        return new PagingExpList<>(context, config, request, adapter);
    }
}
