package com.susheel.pocketparliament.ui.pages.mp_list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.pages.AbstractPageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MpListFragment extends AbstractPageFragment {


    public MpListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mp_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNavigation("MPs");

    }
}
