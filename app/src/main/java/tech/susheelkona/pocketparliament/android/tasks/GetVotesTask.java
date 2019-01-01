package tech.susheelkona.pocketparliament.android.tasks;

import tech.susheelkona.pocketparliament.services.BillService;
import tech.susheelkona.pocketparliament.model.legislation.Vote;

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
