package tech.susheelkona.pocketparliament.android.tasks;

import android.util.Log;

import tech.susheelkona.pocketparliament.services.BillService;
import tech.susheelkona.pocketparliament.model.legislation.Bill;

import java.util.List;

public class GetBillsTask extends AbstractAsyncTask<String, Void, List<Bill>> {

    private final BillService service = BillService.getInstance();

    private boolean error = false;

    public GetBillsTask() {
        super();
    }

    @Override
    protected List<Bill> doInBackground(String... args) {
        try {
            return service.get(args[0]);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            error = true;
//            getAsyncResponseListener().onTaskError(this.getClass(), e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Bill> bills) {
        if(!error){
            getAsyncResponseListener().onTaskSuccess(this.getClass(), bills);
        } else {
            getAsyncResponseListener().onTaskError(this.getClass(), "Error!");
        }
    }
}
