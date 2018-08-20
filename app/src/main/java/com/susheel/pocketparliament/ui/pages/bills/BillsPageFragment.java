package com.susheel.pocketparliament.ui.pages.bills;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.ui.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.ui.fragments.bills.BillsListFragment;
import com.susheel.pocketparliament.ui.pages.AbstractPageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillsPageFragment extends AbstractPageFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter adapter;

    public BillsPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills_page, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(false);
        loadNavigation("Bills");
        addPages(getView());
    }

    private void addPages(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new TabPagerAdapter(getChildFragmentManager());
        adapter.add("Recent", BillsListFragment.forRecent());
        adapter.add("New", BillsListFragment.forNew(true));
        adapter.add("Passed", BillsListFragment.forLaw(true));
        adapter.add("Committees", BillsListFragment.forRecent());
        adapter.add("Following", BillsListFragment.forFollowed(true));

        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
