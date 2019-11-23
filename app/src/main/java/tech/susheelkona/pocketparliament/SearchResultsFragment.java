package tech.susheelkona.pocketparliament;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.susheelkona.pocketparliament.android.adapters.TabPagerAdapter;
import tech.susheelkona.pocketparliament.android.fragments.bills.BillsListFragment;
import tech.susheelkona.pocketparliament.android.fragments.mps.MpListFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {

    private static final String QUERY = "param1";
    private static final String SELECTED = "param2";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter adapter;

    private String query;
    private int selected;


    public SearchResultsFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(false);
        addPages(getView());
        tabLayout.getTabAt(selected).select();
    }

    public void addPages(View v) {
        viewPager = v.findViewById(R.id.view_pager);
        adapter = new TabPagerAdapter(getChildFragmentManager());

        MpListFragment mps = MpListFragment.forSearch(query);
        BillsListFragment bills = BillsListFragment.forSearch(query);

        adapter.add("MPs", MpListFragment.forSearch(query));
        adapter.add("Bills", BillsListFragment.forSearch(query));

        viewPager.setAdapter(adapter);
        tabLayout = v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public int getSelectedPage() {
        return tabLayout.getSelectedTabPosition();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param query Parameter 1.
     * @return A new instance of fragment SearchResultsFragment.
     */
    public static SearchResultsFragment forSearch(String query, int selected) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(QUERY, query);
        args.putInt(SELECTED, selected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(QUERY);
            selected = getArguments().getInt(SELECTED);
        }
    }

}
