package tech.susheelkona.pocketparliament.android.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author Susheel Kona
 */

public class RestorableLayoutManager extends LinearLayoutManager {

    public RestorableLayoutManager(Context context) {
        super(context);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }
}
