package tech.susheelkona.pocketparliament.android.tasks;

import android.os.AsyncTask;

/**
 * @author Susheel Kona
 */

abstract class AbstractAsyncTask<A, B, C> extends AsyncTask<A, B, C> {
    private AsyncResponseListener<C> asyncResponseListener;
    private Exception exception = null;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setAsyncResponseListener(AsyncResponseListener<C> asyncResponseListener) {
        this.asyncResponseListener = asyncResponseListener;
    }

    public AsyncResponseListener<C> getAsyncResponseListener() {
        return asyncResponseListener;
    }


    @Override
    protected void onPostExecute(C returnVal) {
        if(exception == null){
            getAsyncResponseListener().onTaskSuccess(this.getClass(), returnVal);
        } else {
            getAsyncResponseListener().onTaskError(this.getClass(), exception.getMessage());
        }
    }
}
