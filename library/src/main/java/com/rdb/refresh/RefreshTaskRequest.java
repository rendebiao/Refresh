package com.rdb.refresh;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

public abstract class RefreshTaskRequest<D> extends RefreshRequest<D> {

    public RefreshTaskRequest() {

    }

    @Override
    protected void doRequest(final Context context, final int page, final int rowCount) {
        new AsyncTask<Void, Void, ArrayList<D>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                notifyRequestStart();
            }

            @Override
            protected ArrayList<D> doInBackground(Void... params) {
                return RefreshTaskRequest.this.doInBackground(context, page, rowCount);
            }

            @Override
            protected void onPostExecute(ArrayList<D> ds) {
                super.onPostExecute(ds);
                notifyRequestSuccess(ds);
            }
        }.execute();
    }

    protected abstract ArrayList<D> doInBackground(Context context, int page, int rowCount);
}
