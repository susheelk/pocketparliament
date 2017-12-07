package com.susheel.pocketparliament.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.filters.Filter;
import com.susheel.pocketparliament.services.filters.FilterType;
import com.susheel.pocketparliament.services.filters.MemberParliamentFilter;
import com.susheel.pocketparliament.ui.adapters.MpListAdapter;
import com.susheel.pocketparliament.ui.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.ui.tasks.GetMemberParliamentTask;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MpListFragment extends Fragment {

    Filter<MemberParliament> filter;
    List<MemberParliament> members;
    GetMemberParliamentTask task;

    // Views
    RecyclerView recyclerView;

    MpListAdapter adapter;

    public MpListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        filter = new MemberParliamentFilter();
        return inflater.inflate(R.layout.fragment_mp_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        filter.fromBundle(getArguments());
        getData(view);

    }

    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
    }

    private void setupUI(View view) {
        bindViews(view);
        setupRecyclerView();
    }

    private void bindViews(View parentView) {
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_view);
    }

    private void setupRecyclerView() {
        adapter = new MpListAdapter(members);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void getData(View view) {
        task = new GetMemberParliamentTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<MemberParliament>>() {
            @Override
            public void onTaskSuccess(Class source, List<MemberParliament> data) {
                members = data;
//                try {
//                    Thread.sleep(150);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                setupUI(view);
            }

            @Override
            public void onTaskError(Class source, List<MemberParliament> data) {

            }
        });

        task.execute(filter);
    }

    /**
     *
     * @return a fragment displaying all MPs
     */
    public static Fragment forAll() {
        Bundle bundle = new Bundle();
        Fragment fragment = new MpListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     *
     * @param type ie Government, Opposition, Cabinet
     * @return a fragment for that group
     */
    public static Fragment forGroup(String type, boolean value) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(type, value);
        Fragment fragment = new MpListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
