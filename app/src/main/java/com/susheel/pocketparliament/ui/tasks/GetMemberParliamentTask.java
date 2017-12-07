package com.susheel.pocketparliament.ui.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.MemberParliamentService;
import com.susheel.pocketparliament.services.filters.Filter;

import java.util.List;
import java.util.Set;

/**
 * @author Susheel Kona
 */

public class GetMemberParliamentTask extends AbstractAsyncTask<Filter<MemberParliament>, Void, List<MemberParliament>>{

    private MemberParliamentService service = MemberParliamentService.getInstance();


    @Override
    protected List<MemberParliament> doInBackground(Filter<MemberParliament>... params) {
        Log.i("GetMemberParliamentTask", "doInBackground");
        try {
            return service.get(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<MemberParliament> list) {
        getAsyncResponseListener().onTaskSuccess(this.getClass(), list);
    }
}
