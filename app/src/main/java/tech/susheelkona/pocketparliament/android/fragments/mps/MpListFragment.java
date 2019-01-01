package tech.susheelkona.pocketparliament.android.fragments.mps;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import tech.susheelkona.pocketparliament.android.tasks.GetVoteTask;
import tech.susheelkona.pocketparliament.model.MemberParliament;
import tech.susheelkona.pocketparliament.services.filters.FilterParameters;
import tech.susheelkona.pocketparliament.services.filters.MemberParliamentFilter;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.model.legislation.Vote;
import tech.susheelkona.pocketparliament.services.filters.Filter;
import tech.susheelkona.pocketparliament.android.activities.MemberParliamentActivity;
import tech.susheelkona.pocketparliament.android.adapters.MpListAdapter;
import tech.susheelkona.pocketparliament.android.adapters.RecyclerViewListener;
import tech.susheelkona.pocketparliament.android.tasks.AsyncResponseListener;
import tech.susheelkona.pocketparliament.android.tasks.GetMemberParliamentTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MpListFragment extends Fragment {

    Filter<MemberParliament> filter;
    List<MemberParliament> members;
    GetMemberParliamentTask task;

    // Views
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView noConnectionText;

    MpListAdapter adapter;
    RecyclerView.LayoutManager manager;

    AsyncResponseListener listener = new AsyncResponseListener<List<MemberParliament>>() {

        @Override
        public void onTaskSuccess(Class source, List<MemberParliament> data) {
            members = data;
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

//            adapter.setFollowingFirst(getArguments().getBoolean(FilterParameters.FOLLOWING_FIRST));
            adapter.update(data);
        }

        @Override
        public void onTaskError(Class source, String error) {
            System.out.println(error);
            progressBar.setVisibility(View.GONE);
            noConnectionText.setVisibility(View.VISIBLE);
        }
    };

    public MpListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        HashMap<String, Object> map = (HashMap<String, Object>) (getArguments().getSerializable(FilterParameters.FILTER));
        filter = new MemberParliamentFilter(map, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mp_list, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        task.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        getData(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        setupRecyclerView();
        getData(view);
    }

    private void bindViews(View parentView) {
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progress_bar);
        noConnectionText = (TextView) parentView.findViewById(R.id.no_connection);
        noConnectionText.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        adapter = new MpListAdapter((AppCompatActivity)getActivity());
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter.addRecyclerViewListener(new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {
                MemberParliament memberParliament = (MemberParliament) object;
                System.out.println(memberParliament.getName()+" Clicked");
                gotoActivity(memberParliament);
            }

            @Override
            public void onBottomReached() {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void getData(View view) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        task = new GetMemberParliamentTask();
        task.setAsyncResponseListener(listener);

        String result = getArguments().getString(FilterParameters.RESULT);
        if(result != null){
            int voteId = getArguments().getInt(FilterParameters.VOTE_ID);
            buildForVote(voteId, result, filter);
        } else {
            task.execute(filter);
        }
    }

    private void buildForVote(int voteId, String result, Filter<MemberParliament> filter) {
        GetVoteTask voteTask = new GetVoteTask();
        voteTask.setAsyncResponseListener(new AsyncResponseListener<Vote>() {
            @Override
            public void onTaskSuccess(Class source, Vote data) {
                if(result.matches("Yea")){
                    filter.addConstraint(FilterParameters.VOTED_FOR, data);
                } else{
                    filter.addConstraint(FilterParameters.VOTED_AGAINST, data);
                }
                GetMemberParliamentTask task = new GetMemberParliamentTask();
                task.setAsyncResponseListener(listener);
                task.execute(filter);
            }

            @Override
            public void onTaskError(Class source, String message) {

            }
        });
        voteTask.execute(voteId);
    }

    private void setUpTask(GetMemberParliamentTask task) {

    }

    private void gotoActivity(MemberParliament memberParliament){
        Intent intent = new Intent(getActivity(), MemberParliamentActivity.class);
        Bundle args = new Bundle();
        args.putString(FilterParameters.URL, memberParliament.getApiUrl());
        args.putInt(FilterParameters.COLOR, memberParliament.getParty().getColor());
        intent.putExtras(args);
        startActivity(intent);
    }

    /** All MPs
     *
     * @return a Fragment displaying all MPs
     */
    public static Fragment forAll() {
        Bundle bundle = new Bundle();
        Fragment fragment = new MpListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**  For a a subset of MPs
     *
     * @param type ie Government, Opposition, Cabinet
     * @return a Fragment for that group
     */
    public static Fragment forGroup(String type) {
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(type, value);
//        Fragment fragment = new MpListFragment();
//        fragment.setArguments(bundle);
        Map<String, Object> map = new HashMap<>();
        map.put(FilterParameters.GROUP, type);
        return withFilterMap(map);
    }


    /** Searches mps based on query
     *
     * @param query
     * @return
     */
    public static Fragment forSearch(String query) {
        Map<String, Object> map = new HashMap<>();
        map.put(FilterParameters.QUERY, query);
        return withFilterMap(map);
    }

    /** For one MP
     *
     * @param
     * @return
     */
    public static Fragment forOne(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put(FilterParameters.NAME, name);
        return withFilterMap(map);
    }

    public static Fragment forFollowed(){
        Map<String, Object> map = new HashMap<>();
        map.put(FilterParameters.FOLLOWED, true);
        return withFilterMap(map);
    }



    /** For a fragment with specified filters. Use instead of forAll() and forGroup()
     *
     * @param map
     * @return
     */
    public static Fragment withFilterMap(Map<String, Object> map) {
        HashMap<String, Object> hashMap = (HashMap)map;
        Bundle bundle = new Bundle();
        bundle.putSerializable(FilterParameters.FILTER, hashMap);
        bundle.putBoolean(FilterParameters.FOLLOWING_FIRST, true);
        Fragment fragment = new MpListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment forVote(int id, String result) {
        Fragment fragment = new MpListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FilterParameters.VOTE_ID, id);
        bundle.putString(FilterParameters.RESULT, result);
//        bundle.putBoolean(FilterParameters.FOLLOWING_FIRST, true);
        bundle.putSerializable(FilterParameters.FILTER, new HashMap<>());
        fragment.setArguments(bundle);
        return fragment;
    }


}
