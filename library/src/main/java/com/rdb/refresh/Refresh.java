package com.rdb.refresh;

import com.rdb.refresh.view.LoadController;

public class Refresh {

    private static LoadController defaultLoadController;
    private static LoadController listLoadController;
    private static LoadController gridLoadController;
    private static LoadController recyclerLoadController;
    private static LoadController expandableListLoadController;

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
}
