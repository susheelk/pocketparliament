package com.susheel.pocketparliament.ui.tasks;

import com.susheel.pocketparliament.model.legislation.Vote;
import com.susheel.pocketparliament.services.BillService;

import java.util.List;

public class GetVotesTask extends AbstractAsyncTask<String, Void, List<Vote>> {

    private final BillService service = BillService.getInstance();
    private Exception exception = null;

    @Override
    protected List<Vote> doInBackground(String... strings) {
        try {
            return service.getVotes(strings[0]);
        } catch (Exception e) {
           exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Vote> votes) {
        if(exception == null){
            getAsyncResponseListener().onTaskSuccess(this.getClass(), votes);
        } else {
            getAsyncResponseListener().onTaskError(this.getClass(), exception.getMessage());
        }
    }
}
