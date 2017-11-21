package com.susheel.pocketparliament.fragments.pages;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Susheel on 11/20/2017.
 */

public abstract class AbstractPageFragment extends Fragment {

    private AppCompatActivity parentActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.parentActivity = (AppCompatActivity) getActivity();

    }
}
