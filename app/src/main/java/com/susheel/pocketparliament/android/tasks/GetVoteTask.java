package com.susheel.pocketparliament.android.tasks;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.susheel.pocketparliament.model.legislation.Vote;
import com.susheel.pocketparliament.services.BillService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetVoteTask extends AbstractAsyncTask<Integer, Void, Vote> {

    private final BillService service = BillService.getInstance();

    private static List<Vote> cache = new ArrayList<>();

    @Override
    protected Vote doInBackground(Integer... ints) {
        try {
            List<Vote> list = Stream.of(cache).filter(v -> v.getId() == ints[0]).collect(Collectors.toList());
            if(list != null && !list.isEmpty()){
                return list.get(0);
            }

            Vote vote =  service.getVote(ints[0]);
            cache.add(vote);
            return vote;
        } catch (Exception e) {
            e.printStackTrace();
            setException(e);
        }
        return null;
    }
}