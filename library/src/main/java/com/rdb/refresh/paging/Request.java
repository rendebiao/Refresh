package com.rdb.refresh.paging;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public abstract class Request<D> {

    private Paging paging;

    final void setPaging(Paging paging) {
        this.paging = paging;
    }

    protected abstract void doRequest(Context context, int page, int rowCount);

    public void notifyRequestStart() {
        if (paging != null) {
            paging.onRequestStart();
        }
    }

    public void notifyRequestSuccess(List<D> list) {
        if (paging != null) {
            paging.onRequestSuccess(list);
        }
    }

    public void notifyRequestFailure(int errorType, Exception e) {
        if (paging != null) {
            paging.onRequestFailure(new Error(errorType, e));
        }
    }

    public void notifyRequestCancel() {
        if (paging != null) {
            paging.onRequestCancel();
        }
    }

    public static class Error {

        private int errorType;
        private Exception exception;

        public Error(int errorType, Exception exception) {
            this.errorType = errorType;
            this.exception = exception;
        }

        public int getErrorType() {
            return errorType;
        }

        public Exception getException() {
            return exception;
        }
    }

    public abstract static class TaskRequest<D> extends Request<D> {

        public TaskRequest() {

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
                    return TaskRequest.this.doInBackground(context, page, rowCount);
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
}