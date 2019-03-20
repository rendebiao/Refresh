package com.rdb.refresh;

public class RefreshPage {
    final int startPage;
    final int rowCount;
    int curPage;

    public RefreshPage(int startPage, int rowCount) {
        if (rowCount <= 0) {
            throw new RuntimeException("RefreshPage 参数不合法");
        }
        this.startPage = startPage;
        this.rowCount = rowCount;
    }
}
