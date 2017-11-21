package com.susheel.pocketparliament.fragments.pages;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.susheel.pocketparliament.R;

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
//        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//
//        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout,R.string.app_name, R.string.hello_blank_fragment);
//        drawerToggle.setDrawerIndicatorEnabled(true);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        activity.getSupportActionBar().setTitle("MPs");


//        parentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
