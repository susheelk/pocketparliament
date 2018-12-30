package com.susheel.pocketparliament.android.fragments;


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
import android.widget.Toast;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.android.adapters.BillsListAdapter;
import com.susheel.pocketparliament.android.adapters.NewsListAdapter;
import com.susheel.pocketparliament.android.adapters.RecyclerViewListener;
import com.susheel.pocketparliament.android.tasks.AsyncResponseListener;
import com.susheel.pocketparliament.android.tasks.GetBillsTask;
import com.susheel.pocketparliament.android.tasks.GetNewsTask;
import com.susheel.pocketparliament.model.NewsItem;
import com.susheel.pocketparliament.model.legislation.Bill;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    // Data
    private GetNewsTask task;
    private int page = 1;

    // Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    // Adapters
    private RecyclerView.LayoutManager manager;
    private NewsListAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        setUpRecyclerView();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
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

    public void setUpRecyclerView(){
        adapter = new NewsListAdapter((AppCompatActivity)getActivity());
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter.addRecyclerViewListener(new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {
//                goToActivity((Bill)(object));
            }

            @Override
            public void onBottomReached() {
//                addData();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    public void getData(){
        page = 1;
        swipeRefreshLayout.setRefreshing(true);
        task = new GetNewsTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<NewsItem>>() {
            @Override
            public void onTaskSuccess(Class source, List<NewsItem> data) {
                Log.i("getNews", data.size()+"");
                adapter.update(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("getNews", message);
                displayError();
            }
        });
        task.execute("");
    }

    public void addData(){
        swipeRefreshLayout.setRefreshing(true);
        task = new GetNewsTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<NewsItem>>() {
            @Override
            public void onTaskSuccess(Class source, List<NewsItem> data) {
                Log.i("getNews", data.size()+"");
                adapter.add(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("getNews", message);
                displayError();
            }
        });
        task.execute("&page="+(++page));
    }

    private synchronized void displayError(){
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
    }



}
