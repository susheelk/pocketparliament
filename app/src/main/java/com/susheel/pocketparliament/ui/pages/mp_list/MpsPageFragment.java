package com.susheel.pocketparliament.ui.pages.mp_list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;

import com.susheel.pocketparliament.services.filters.FilterParameters;
import com.susheel.pocketparliament.ui.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.ui.fragments.MpListFragment;
import com.susheel.pocketparliament.ui.pages.AbstractPageFragment;



/**
 * A simple {@link Fragment} subclass.
 */
public class MpsPageFragment extends AbstractPageFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MpsPageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mps_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNavigation("MPs");

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        TabPagerAdapter adapter = new TabPagerAdapter(getFragmentManager());
//        adapter.add("Favourites", new FavouriteMpTabFragment());
//        adapter.add("All", new AllMpTabFragment());
        adapter.add("All", MpListFragment.forAll());
        adapter.add("Cabinet", MpListFragment.forGroup(FilterParameters.GOVERNMENT, true));
        adapter.add("Government", MpListFragment.forGroup(FilterParameters.GOVERNMENT, true));
        adapter.add("Opposition", MpListFragment.forGroup(FilterParameters.GOVERNMENT, false));
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


}

