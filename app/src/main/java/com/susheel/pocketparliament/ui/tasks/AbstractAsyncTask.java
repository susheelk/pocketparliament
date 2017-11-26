package com.susheel.pocketparliament.ui.tasks;

import android.os.AsyncTask;

import com.susheel.pocketparliament.model.MemberParliament;

/**
 * @author Susheel Kona
 */

abstract class AbstractAsyncTask<A, B, C> extends AsyncTask<A, B, C> {
    private AsyncResponseListener<MemberParliament[]> asyncResponseListener;

    public void setAsyncResponseListener(AsyncResponseListener<MemberParliament[]> asyncResponseListener) {
        this.asyncResponseListener = asyncResponseListener;
    }

    public AsyncResponseListener<MemberParliament[]> getAsyncResponseListener() {
        return asyncResponseListener;
    }
}
