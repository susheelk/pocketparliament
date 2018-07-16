package com.susheel.pocketparliament.ui.tasks;

import android.util.Log;

import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.services.BillService;

import java.util.List;

public class GetBillsTask extends AbstractAsyncTask<String, Void, List<Bill>> {

    private final BillService service = BillService.getInstance();

    public GetBillsTask() {
        super();
    }

    @Override
    protected List<Bill> doInBackground(String... args) {
        try {
            return service.get(args[0]);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
//            getAsyncResponseListener().onTaskError(this.getClass(), e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Bill> bills) {
        getAsyncResponseListener().onTaskSuccess(this.getClass(), bills);
    }
}
