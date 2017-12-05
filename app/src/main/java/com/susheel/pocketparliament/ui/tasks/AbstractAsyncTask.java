package com.susheel.pocketparliament.ui.tasks;

import android.os.AsyncTask;

import com.susheel.pocketparliament.model.MemberParliament;

/**
 * @author Susheel Kona
 */

abstract class AbstractAsyncTask<A, B, C> extends AsyncTask<A, B, C> {
    private AsyncResponseListener<C> asyncResponseListener;

    public void setAsyncResponseListener(AsyncResponseListener<C> asyncResponseListener) {
        this.asyncResponseListener = asyncResponseListener;
    }

    public AsyncResponseListener<C> getAsyncResponseListener() {
        return asyncResponseListener;
    }
}
