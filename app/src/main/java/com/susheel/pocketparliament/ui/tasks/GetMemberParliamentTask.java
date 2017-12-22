package com.susheel.pocketparliament.ui.tasks;

import android.util.Log;

import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.MemberParliamentService;
import com.susheel.pocketparliament.services.filters.Filter;
import com.susheel.pocketparliament.services.filters.FilterParameters;
import com.susheel.pocketparliament.services.filters.MemberParliamentFilter;

import java.util.Arrays;
import java.util.List;


/**
 * @author Susheel Kona
 */

public class GetMemberParliamentTask extends AbstractAsyncTask<Filter<MemberParliament>, Void, List<MemberParliament>>{

    private MemberParliamentService service = MemberParliamentService.getInstance();
    private static List<MemberParliament>  cache = null;

    @Override
    protected List<MemberParliament> doInBackground(Filter<MemberParliament>... params) {
        Log.i("GetMemberParliamentTask", "doInBackground");

        if (GetMemberParliamentTask.cache != null) {
            return cache;
        }

        try {
            MemberParliamentFilter filter = (MemberParliamentFilter)params[0];
            if (filter != null) {
//                if (filter.isForOne()){
//                    return Arrays.asList(service.getUniqueByName(filter.getConstraint(FilterParameters.NAME).toString()));
//                }
                if (filter.containsUrl()) {
                    cache =  Arrays.asList(service.getUniqueByUrl(filter.getConstraint(FilterParameters.URL).toString()));

                }
                cache =  service.get(params[0]);
                return cache;
            }
            throw new IllegalArgumentException("No filter was provided");
        } catch (Exception e) {
            this.cancel(true);
            Log.e("ERROR", e.getMessage());
            getAsyncResponseListener().onTaskError(this.getClass(), e.getMessage());
        }

        return null;
    }




    @Override
    protected void onPostExecute(List<MemberParliament> list) {
        getAsyncResponseListener().onTaskSuccess(this.getClass(), list);
    }
}
