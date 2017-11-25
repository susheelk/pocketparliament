package com.susheel.pocketparliament.ui.pages.mp_list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllMpTabFragment extends Fragment {


    public AllMpTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_mp_tab, container, false);
    }

    private void getData() {

    }

}
