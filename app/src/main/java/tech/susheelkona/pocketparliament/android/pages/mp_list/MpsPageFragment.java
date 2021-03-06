package tech.susheelkona.pocketparliament.android.pages.mp_list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.susheelkona.pocketparliament.android.adapters.TabPagerAdapter;
import tech.susheelkona.pocketparliament.android.fragments.mps.MpListFragment;
import tech.susheelkona.pocketparliament.services.filters.FilterParameters;

import tech.susheelkona.pocketparliament.android.pages.AbstractPageFragment;



/**
 * A simple {@link Fragment} subclass.
 */
public class MpsPageFragment extends AbstractPageFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter adapter;

    public MpsPageFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(tech.susheelkona.pocketparliament.R.layout.fragment_mps_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(false);
        loadNavigation("MPs");
        addPages(getView());


    }


    @Override
    public void onResume() {

        super.onResume();


    }

    private void addPages(View view) {
        viewPager = (ViewPager) view.findViewById(tech.susheelkona.pocketparliament.R.id.view_pager);
//        viewPager.setSaveFromParentEnabled(false);
        adapter = new TabPagerAdapter(getChildFragmentManager());
//        adapter.add("Favourites", new FavouriteMpTabFragment());
//        adapter.add("All", new AllMpTabFragment());
        adapter.add("All", MpListFragment.forAll());
        adapter.add("Cabinet", MpListFragment.forGroup(FilterParameters.CABINET));
        adapter.add("Government", MpListFragment.forGroup(FilterParameters.GOVERNMENT));
        adapter.add("Opposition", MpListFragment.forGroup(FilterParameters.OPPOSITION));
        viewPager.setAdapter(adapter);
//        viewPager.setSaveFromParentEnabled(false);

        tabLayout = (TabLayout) view.findViewById(tech.susheelkona.pocketparliament.R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }
}

