package com.rdb.refresh.demo;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rdb.refresh.paging.Request;

import java.util.ArrayList;
import java.util.List;

//Volley请求组合示例
public class VolleyRequest<D> extends Request<D> implements Response.Listener<String>, Response.ErrorListener {

    private String url;
    private Class<D> dClass;
    private RequestQueue requestQueue;

    public VolleyRequest(RequestQueue requestQueue, String url, Class<D> dClass) {
        this.url = url;
        this.dClass = dClass;
        this.requestQueue = requestQueue;
    }

    @Override
    protected void doRequest(Context context, int page, int rowCount) {
        notifyRequestStart();
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url, this, this);
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    @Override
    public void onResponse(String response) {
        try {
            List<D> list = JsonUtil.toArrayUnCatch(response, dClass);
            if (list == null) {
                list = new ArrayList<>();
            }
            notifyRequestSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            notifyRequestFailure(1, e);//解析异常
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        notifyRequestFailure(0, error);//Volley异常
    }
}
