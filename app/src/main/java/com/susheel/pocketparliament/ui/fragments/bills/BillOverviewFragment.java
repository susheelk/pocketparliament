package com.susheel.pocketparliament.ui.fragments.bills;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.BillActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillOverviewFragment extends Fragment {


    public BillOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill_overview, container, false);
    }

    public static Fragment forBill(int id) {
        Fragment fragment = new BillOverviewFragment();
        Bundle args = new Bundle();
        args.putInt(BillActivity.ID, id);
        fragment.setArguments(args);
        return fragment;
    }

}
