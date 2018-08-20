package com.susheel.pocketparliament.ui.pages.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.ui.fragments.NewsFragment;
import com.susheel.pocketparliament.ui.fragments.bills.BillsListFragment;
import com.susheel.pocketparliament.ui.fragments.mps.MpListFragment;
import com.susheel.pocketparliament.ui.pages.AbstractPageFragment;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AbstractPageFragment {

    private ImageView wall;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabPagerAdapter adapter;

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
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // Idek what this does but stack overflow came clutch
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(verticalOffset == 0 || verticalOffset <= toolbar.getHeight() && !toolbar.getTitle().equals("")){
//                    toolbar.setTitle("");
//                } else if(!toolbar.getTitle().equals(toolbar)){
//                    toolbar.setTitle("Ttt");
//                }
//
//            }
//        });

        loadFollowed();
        wall = (ImageView) view.findViewById(R.id.main_image);

        int id = 0;

        switch (new Random().nextInt(4)+1){
            case 1: id = R.drawable.wall_2; break;
            case 2: id = R.drawable.wall_2; break;
            case 3: id = R.drawable.wall_3; break;
            case 4: id = R.drawable.wall_4; break;
            default: id = R.drawable.wall_2;
        }
        wall.setImageDrawable(getResources().getDrawable(id));
        addPages(view);
    }

    private void loadFollowed(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.followed_mp_frame, MpListFragment.forFollowed());
//        transaction.replace(R.id.followed_bills_frame, BillsListFragment.forFollowed(true));
        transaction.commit();
    }

    private void addPages(View view){
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new TabPagerAdapter(getChildFragmentManager());
//        adapter.add("News", BillsListFragment.forRecent());
        adapter.add("News", new NewsFragment());
        adapter.add("Following", MpListFragment.forFollowed());

        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }



}
