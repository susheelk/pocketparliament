package com.susheel.pocketparliament.ui.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.MemberParliamentService;

/**
 * @author Susheel Kona
 */

public class GetMemberParliamentTask extends AbstractAsyncTask<String, Void, MemberParliament[]>{

    private MemberParliamentService service = MemberParliamentService.getInstance();

    @Override
    protected MemberParliament[] doInBackground(String... params) {
        Log.i("GetMemberParliamentTask", "doInBackground");
        try {
            return service.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(MemberParliament[] memberParliaments) {
        getAsyncResponseListener().onTaskSuccess(this.getClass(), memberParliaments);
    }
}
