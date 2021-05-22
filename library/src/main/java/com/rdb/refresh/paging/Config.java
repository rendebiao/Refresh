package com.rdb.refresh.paging;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;

import com.rdb.refresh.Refresh;
import com.rdb.refresh.view.RefreshLayout;

public class Config {

    public static final int NULL = 0;
    public static final int EMPTY = 1;
    public static final int LAYOUT = 2;
    public static final int LAYOUT_EMPTY = 3;
    public static final int LAYOUT_STUB_EMPTY = 4;

    @Type
    final int type;
    @LayoutRes
    int layoutRes;
    @IdRes
    int refreshViewId;
    @IdRes
    int emptyViewId;
    @IdRes
    int emptyStubId;
    @LayoutRes
    int emptyLayoutRes;
    @RefreshLayout.Mode
    int refreshMode;

    int startPage = 1;
    int rowCount = 10;

    /**
     * 自动创建刷新控件 无空控件
     *
     * @param refreshMode
     */
    public Config(@RefreshLayout.Mode int refreshMode) {
        startPage = Refresh.getStartPage();
        rowCount = Refresh.getRowCount();
        this.refreshMode = refreshMode;
        type = NULL;
        setPageInfo(Refresh.getStartPage(), Refresh.getRowCount());
    }

    /**
     * 自动创建刷新控件 并加载指定空控件
     *
     * @param refreshMode
     * @param emptyLayoutRes 空控件布局文件
     */
    public Config(@RefreshLayout.Mode int refreshMode, @LayoutRes int emptyLayoutRes) {
        this.refreshMode = refreshMode;
        this.emptyLayoutRes = emptyLayoutRes;
        type = EMPTY;
        setPageInfo(Refresh.getStartPage(), Refresh.getRowCount());
    }

    /**
     * 从指定布局加载刷新控件 无空控件
     *
     * @param refreshMode
     * @param layoutRes     布局文件
     * @param refreshViewId 刷新控件ID
     */
    public Config(@RefreshLayout.Mode int refreshMode, @LayoutRes int layoutRes, @IdRes int refreshViewId) {
        this.refreshMode = refreshMode;
        this.layoutRes = layoutRes;
        this.refreshViewId = refreshViewId;
        type = LAYOUT;
        setPageInfo(Refresh.getStartPage(), Refresh.getRowCount());
    }

    /**
     * 从指定布局加载刷新控件、空控件
     *
     * @param refreshMode
     * @param layoutRes     布局文件
     * @param refreshViewId 刷新控件ID
     * @param emptyViewId   空控件ID 或者指定了layout的ViewStub控件ID
     */
    public Config(@RefreshLayout.Mode int refreshMode, @LayoutRes int layoutRes, @IdRes int refreshViewId, @IdRes int emptyViewId) {
        this.refreshMode = refreshMode;
        this.layoutRes = layoutRes;
        this.refreshViewId = refreshViewId;
        this.emptyViewId = emptyViewId;
        type = LAYOUT_EMPTY;
        setPageInfo(Refresh.getStartPage(), Refresh.getRowCount());
    }

    /**
     * 从指定布局加载刷新控件，ViewStub控件，空控件布局
     *
     * @param refreshMode
     * @param layoutRes      布局文件
     * @param refreshViewId  刷新控件ID
     * @param emptyStubId    ViewStub控件ID
     * @param emptyLayoutRes 空控件布局文件
     */
    public Config(@RefreshLayout.Mode int refreshMode, @LayoutRes int layoutRes, @IdRes int refreshViewId, @IdRes int emptyStubId, @LayoutRes int emptyLayoutRes) {
        this.refreshMode = refreshMode;
        this.layoutRes = layoutRes;
        this.refreshViewId = refreshViewId;
        this.emptyStubId = emptyStubId;
        this.emptyLayoutRes = emptyLayoutRes;
        type = LAYOUT_STUB_EMPTY;
        setPageInfo(Refresh.getStartPage(), Refresh.getRowCount());
    }

    public void setPageInfo(int startPage, int rowCount) {
        this.startPage = startPage;
        this.rowCount = rowCount;
    }

    @IntDef({NULL, EMPTY, LAYOUT, LAYOUT_EMPTY, LAYOUT_STUB_EMPTY})
    public @interface Type {

    }
}
