package com.susheel.pocketparliament.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillOverview extends Fragment {


    public BillOverview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_overview, container, false);
    }

}
