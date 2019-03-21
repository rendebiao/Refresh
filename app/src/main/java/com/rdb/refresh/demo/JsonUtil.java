package com.rdb.refresh.demo;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    /**
     * 将json字符串解析为对象数组
     *
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> toArray(String json, Class<T> classOfT) {
        ArrayList<T> ts = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                ts = (ArrayList<T>) JSON.parseArray(json, classOfT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ts;
    }

    public static <T> List<T> toArrayUnCatch(String json, Class<T> classOfT) throws Exception {
        return JSON.parseArray(json, classOfT);
    }
}
