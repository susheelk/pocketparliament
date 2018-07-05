package com.susheel.pocketparliament.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.ui.adapters.BillsListAdapter;
import com.susheel.pocketparliament.ui.adapters.RecyclerViewListener;
import com.susheel.pocketparliament.ui.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.ui.tasks.GetBillsTask;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 */
public class BillsListFragment extends Fragment {

    // Data
    private List<Bill> bills;
    private GetBillsTask task;

    // Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    // Adapters
    private RecyclerView.LayoutManager manager;
    private BillsListAdapter adapter;


    public BillsListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bills_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        bindViews(view);
        getData(view);
    }

    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
    }

    private void bindViews(View parent) {
        swipeRefreshLayout = (SwipeRefreshLayout) parent.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parent.setVisibility(View.INVISIBLE);
                getData(parent);
                parent.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setUpRecyclerView(){
        adapter = new BillsListAdapter(bills, (AppCompatActivity)getActivity());
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter.addRecyclerViewListener(new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void getData(View view) {
        swipeRefreshLayout.setRefreshing(true);
        task = new GetBillsTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<Bill>>() {
            @Override
            public void onTaskSuccess(Class source, List<Bill> data) {
                Log.i("getBills", data.size()+"");
                bills = data;
                setUpRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("getBills", message+"");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        task.execute();
    }
}
