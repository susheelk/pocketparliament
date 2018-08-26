package com.susheel.pocketparliament.android.tasks;

import com.susheel.pocketparliament.model.legislation.Vote;
import com.susheel.pocketparliament.services.BillService;

import java.util.List;

public class GetVotesTask extends AbstractAsyncTask<String, Void, List<Vote>> {

    private final BillService service = BillService.getInstance();

    @Override
    protected List<Vote> doInBackground(String... strings) {
        try {
            return service.getVotes(strings[0]);
        } catch (Exception e) {
           setException(e);
        }
        return null;
    }


}
