package tech.susheelkona.pocketparliament.android.fragments;


import android.content.Intent;
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

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import tech.susheelkona.pocketparliament.android.activities.BillActivity;
import tech.susheelkona.pocketparliament.android.adapters.NewsListAdapter;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.android.adapters.RecyclerViewListener;
import tech.susheelkona.pocketparliament.android.tasks.AsyncResponseListener;
import tech.susheelkona.pocketparliament.android.tasks.GetNewsTask;
import tech.susheelkona.pocketparliament.android.tasks.SharedPreferenceHelper;
import tech.susheelkona.pocketparliament.model.NewsItem;
import tech.susheelkona.pocketparliament.model.legislation.Bill;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements Refreshable{

    // Data
    private GetNewsTask task;
    private int page = 1;
    private SharedPreferenceHelper preferences = SharedPreferenceHelper.getInstance();

    // Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View noContentView;

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
                refresh();
            }
        });
        noContentView = parent.findViewById(R.id.no_content);
        noContentView.setVisibility(View.GONE);
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
                NewsItem newsItem = (NewsItem) object;
                Bill bill = new Bill();
                bill.setTitle(newsItem.getDescription());
                bill.setNumber(newsItem.getBillNumber());
                bill.setId(newsItem.getBillId());
                goToBillActivity(bill);
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
                if (data != null){
                    Log.i("getNews", data.size()+"");
                    if (getArguments() != null
                            && (getArguments().containsKey(SharedPreferenceHelper.FOLLOWED_ONLY))
                            && (getArguments().getBoolean(SharedPreferenceHelper.FOLLOWED_ONLY))
                            && getContext() != null){
                        List<String> bills = preferences.getFollowedBills(getContext());
                        data = Stream.of(data).filter(news -> bills.contains(news.getBillNumber())).collect(Collectors.toList());
                    }
                    adapter.update(data);
                    noContentView.setVisibility(data.size() == 0 ? View.VISIBLE : View.GONE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onTaskError(Class source, String message) {
                noContentView.setVisibility(View.VISIBLE);
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
        noContentView.setVisibility(View.VISIBLE);
//        Toast.makeText(getContext(), "No Connection", Toast.LENGTH_SHORT).show();
    }

    private void goToBillActivity(Bill bill){
        Log.i("GOTO", "bill");
        Intent intent = new Intent(getActivity(), BillActivity.class);
        Bundle args = new Bundle();
        args.putString(BillActivity.NUMBER, bill.getNumber());
        args.putString(BillActivity.TITLE, bill.getTitle());
        args.putInt(BillActivity.ID, bill.getId());
        intent.putExtras(args);
        startActivity(intent);
    }

    public static Fragment forFollowed(){
        Fragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putBoolean(SharedPreferenceHelper.FOLLOWED_ONLY, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void refresh() {
        getView().setVisibility(View.INVISIBLE);
        getData();
        getView().setVisibility(View.VISIBLE);
    }
}
