package com.rdb.refresh;

import com.rdb.refresh.view.RefreshLoadController;

public class Refresh {

    private static RefreshLoadController defaultLoadController;
    private static RefreshLoadController listLoadController;
    private static RefreshLoadController gridLoadController;
    private static RefreshLoadController recyclerLoadController;
    private static RefreshLoadController expandableListLoadController;

    public static void setDefaultLoadController(RefreshLoadController defaultLoadController) {
        Refresh.defaultLoadController = defaultLoadController;
    }

    public static RefreshLoadController getListLoadController() {
        return listLoadController == null ? defaultLoadController : listLoadController;
    }

    public static void setListLoadController(RefreshLoadController listLoadController) {
        Refresh.listLoadController = listLoadController;
    }

    public static RefreshLoadController getGridLoadController() {
        return gridLoadController == null ? defaultLoadController : gridLoadController;
    }

    public static void setGridLoadController(RefreshLoadController gridLoadController) {
        Refresh.gridLoadController = gridLoadController;
    }

    public static RefreshLoadController getRecyclerLoadController() {
        return recyclerLoadController == null ? defaultLoadController : recyclerLoadController;
    }

    public static void setRecyclerLoadController(RefreshLoadController recyclerLoadController) {
        Refresh.recyclerLoadController = recyclerLoadController;
    }

    public static RefreshLoadController getExpandableListLoadController() {
        return expandableListLoadController == null ? defaultLoadController : expandableListLoadController;
    }

    public static void setExpandableListLoadController(RefreshLoadController expandableListLoadController) {
        Refresh.expandableListLoadController = expandableListLoadController;
    }
}
