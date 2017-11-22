package com.susheel.pocketparliament.ui.pages.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.pages.AbstractPageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AbstractPageFragment {


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNavigation("Pocket Parliament", true);

        AppBarLayout appBarLayout = (AppBarLayout) getView().findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // Idek what this does but stack overflow came clutch
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0 || verticalOffset <= toolbar.getHeight() && !toolbar.getTitle().equals("")){
                    toolbar.setTitle("");
                } else if(!toolbar.getTitle().equals(toolbar)){
                    toolbar.setTitle("Ttt");
                }

            }
        });

    }


}
