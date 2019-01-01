package tech.susheelkona.pocketparliament.android.tasks;

import tech.susheelkona.pocketparliament.services.BillService;
import tech.susheelkona.pocketparliament.model.legislation.Bill;

public class GetBillTask extends AbstractAsyncTask<Integer, Void, Bill> {

    private final BillService service = BillService.getInstance();
    private boolean error = false;
    private Exception exception;

    @Override
    protected Bill doInBackground(Integer... ints) {
        try {
            return service.getById(ints[0]);
        } catch (Exception e) {
            this.cancel(true);
            error = true;
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bill bill) {
        if (!error) {
            getAsyncResponseListener().onTaskSuccess(this.getClass(), bill);
        } else {
            getAsyncResponseListener().onTaskError(this.getClass(), exception.getMessage());
        }
    }
}
