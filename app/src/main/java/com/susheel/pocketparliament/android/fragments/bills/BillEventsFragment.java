package com.susheel.pocketparliament.android.fragments.bills;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.android.activities.BillActivity;
import com.susheel.pocketparliament.android.adapters.BillEventsAdapter;
import com.susheel.pocketparliament.android.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.android.tasks.GetBillTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillEventsFragment extends Fragment {

    private GetBillTask task;

    // Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    // Adapters
    private RecyclerView.LayoutManager manager;
    private BillEventsAdapter adapter;


    public BillEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        setupRecyclerView();
        getData();

    }

    private void bindViews(View parent) {
        swipeRefreshLayout = (SwipeRefreshLayout) parent.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.senate);
        recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parent.setVisibility(View.INVISIBLE);
                getData();
                parent.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupRecyclerView(){
        adapter = new BillEventsAdapter((AppCompatActivity)(getActivity()));
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        swipeRefreshLayout.setRefreshing(true);
        task = new GetBillTask();
        task.setAsyncResponseListener(new AsyncResponseListener<Bill>() {
            @Override
            public void onTaskSuccess(Class source, Bill data) {
                Log.i("EventsSize ", data.getEvents().size()+"");
                swipeRefreshLayout.setRefreshing(false);
                adapter.update(data.getEvents());
            }

            @Override
            public void onTaskError(Class source, String message) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        task.execute(getArguments().getInt(BillActivity.ID));
    }

    public static Fragment forBill(int id){
        Fragment fragment = new BillEventsFragment();
        Bundle args = new Bundle();
        args.putInt(BillActivity.ID, id);
        fragment.setArguments(args);
        return fragment;
    }

}
