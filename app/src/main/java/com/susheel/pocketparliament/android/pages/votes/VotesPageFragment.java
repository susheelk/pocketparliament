package com.susheel.pocketparliament.android.pages.votes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.android.adapters.TabPagerAdapter;
import com.susheel.pocketparliament.android.fragments.bills.BillsListFragment;
import com.susheel.pocketparliament.android.fragments.votes.VotesListFragment;
import com.susheel.pocketparliament.android.pages.AbstractPageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class VotesPageFragment extends AbstractPageFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter adapter;


    public VotesPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_votes_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(false);
        loadNavigation("Votes");
        addPages(getView());
    }

    private void addPages(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new TabPagerAdapter(getChildFragmentManager());
        adapter.add("Recent", VotesListFragment.forAll());
        adapter.add("Passed", VotesListFragment.forResult("Agreed+to"));
        adapter.add("Failed", VotesListFragment.forResult("Negatived"));

        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
