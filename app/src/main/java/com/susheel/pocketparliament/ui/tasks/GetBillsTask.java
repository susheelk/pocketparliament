package com.susheel.pocketparliament.ui.tasks;

import android.util.Log;

import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.services.BillService;

import java.util.List;

public class GetBillsTask extends AbstractAsyncTask<Void, Void, List<Bill>> {

    private final BillService service = BillService.getInstance();

    @Override
    protected List<Bill> doInBackground(Void... voids) {
        try {
            return service.get();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            getAsyncResponseListener().onTaskError(this.getClass(), e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Bill> bills) {
        getAsyncResponseListener().onTaskSuccess(this.getClass(), bills);
    }
}
