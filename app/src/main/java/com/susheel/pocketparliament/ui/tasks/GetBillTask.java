package com.susheel.pocketparliament.ui.tasks;

import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.services.BillService;

public class GetBillTask extends AbstractAsyncTask<Integer, Void, Bill> {

    private final BillService service = BillService.getInstance();

    @Override
    protected Bill doInBackground(Integer... ints) {
        try {
            return service.getById(ints[0]);
        } catch (Exception e) {
            this.cancel(true);
            getAsyncResponseListener().onTaskError(this.getClass(), e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bill bill) {
        getAsyncResponseListener().onTaskSuccess(this.getClass(), bill);
    }
}
