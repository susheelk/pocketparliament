package com.susheel.pocketparliament.ui.pages;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.MainActivity;

/**
 * Created by Susheel on 11/20/2017.
 */

public class AbstractPageFragment extends Fragment {

    public AppCompatActivity parentActivity;
    public Toolbar toolbar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.parentActivity = (AppCompatActivity) getActivity();

    }

    public void loadNavigation(String title){
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        parentActivity.setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) parentActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(parentActivity, drawerLayout,R.string.app_name, R.string.hello_blank_fragment);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setCollapsible(false);
        parentActivity.getSupportActionBar().setTitle(title);

        parentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public void loadNavigation(String title, boolean transparentActionbar){
        loadNavigation(title);
        if(transparentActionbar){
            parentActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return;
        }
//        parentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
