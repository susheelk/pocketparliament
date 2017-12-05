package com.susheel.pocketparliament.ui.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.MemberParliamentService;

import java.util.Set;

/**
 * @author Susheel Kona
 */

public class GetMemberParliamentTask extends AbstractAsyncTask<String, Void, Set<MemberParliament>>{

    private MemberParliamentService service = MemberParliamentService.getInstance();

    @Override
    protected Set<MemberParliament> doInBackground(String... params) {
        Log.i("GetMemberParliamentTask", "doInBackground");
        try {
            return service.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Set<MemberParliament> set) {
        getAsyncResponseListener().onTaskSuccess(this.getClass(), set);
    }
}
