package com.susheel.pocketparliament.android.tasks;

import com.susheel.pocketparliament.model.legislation.Vote;
import com.susheel.pocketparliament.services.BillService;

public class GetVoteTask extends AbstractAsyncTask<Integer, Void, Vote> {

    private final BillService service = BillService.getInstance();

    @Override
    protected Vote doInBackground(Integer... ints) {
        try {
            return service.getVote(ints[0]);
        } catch (Exception e) {
            e.printStackTrace();
            setException(e);
        }
        return null;
    }
}