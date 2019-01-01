package tech.susheelkona.pocketparliament.android.fragments.votes;


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

import tech.susheelkona.pocketparliament.android.activities.VoteActivity;
import tech.susheelkona.pocketparliament.services.filters.FilterParameters;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.model.legislation.Vote;
import tech.susheelkona.pocketparliament.android.adapters.RecyclerViewListener;
import tech.susheelkona.pocketparliament.android.adapters.VotesListAdapter;
import tech.susheelkona.pocketparliament.android.tasks.AsyncResponseListener;
import tech.susheelkona.pocketparliament.android.tasks.GetVotesTask;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VotesListFragment extends Fragment {

    // Data
    private List<Vote> votes;
    private GetVotesTask task;
    private GetVotesTask addTask;
    private int page = 1;
    private boolean displayVotes = false;

    // Views
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    // Adapters
    private RecyclerView.LayoutManager manager;
    private VotesListAdapter adapter;

    public VotesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_votes_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            displayVotes = getArguments().getBoolean("displayBallots");
        } catch (Exception e) {

        }
        bindViews(view);
        setUpRecyclerView();
        getData(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        getData(view);
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();

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
                getData(parent);
                parent.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setUpRecyclerView(){
        adapter = new VotesListAdapter((AppCompatActivity)getActivity());
        adapter.showBallots(displayVotes);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter.addRecyclerViewListener(new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {
                goToActivity((Vote)(object));
            }

            @Override
            public void onBottomReached() {
                addData();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void getData(View view){
        page = 1;
        swipeRefreshLayout.setRefreshing(true);
        task = new GetVotesTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<Vote>>() {
            @Override
            public void onTaskSuccess(Class source, List<Vote> data) {
                votes = data;
                Log.i("Votes: ", data.size()+"");
                adapter.update(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("ERR", message);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        try {
            task.execute(getArguments().getString("params"));
        } catch (Exception e) {
            task.execute("");
        }
    }

    private void addData(){
        swipeRefreshLayout.setRefreshing(true);
        task = new GetVotesTask();
        task.setAsyncResponseListener(new AsyncResponseListener<List<Vote>>() {
            @Override
            public void onTaskSuccess(Class source, List<Vote> data) {
                votes = data;
                Log.i("Votes: ", data.size()+"");
                adapter.addData(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("ERR", message);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        try {
            task.execute(getArguments().getString("params")+"&page="+(++page));
        } catch (Exception e) {
            task.execute("?page="+(++page));
        }
    }



    private void goToActivity(Vote vote){
        Intent intent = new Intent(getActivity(), VoteActivity.class);
        Bundle args = new Bundle();
        args.putInt(FilterParameters.ID, vote.getId());
        args.putInt("yeas", vote.getYeas());
        args.putInt("nays", vote.getNays());
        intent.putExtras(args);
        startActivity(intent);
    }

    public static Fragment forBill(int billId){
        Fragment fragment = new VotesListFragment();
        Bundle args = new Bundle();
        args.putString("params", "?bill_id="+billId);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment forAll() {
        return new VotesListFragment();
    }

    public static Fragment forResult(String result){
        Fragment fragment = new VotesListFragment();
        Bundle args = new Bundle();
        args.putString("params", "?result="+result);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment forMemberParliament(String name){
        Fragment fragment = new VotesListFragment();
        Bundle args = new Bundle();
        args.putString("params", "?mps="+name);
        args.putBoolean("displayBallots", true);
        fragment.setArguments(args);
        return fragment;
    }

}
