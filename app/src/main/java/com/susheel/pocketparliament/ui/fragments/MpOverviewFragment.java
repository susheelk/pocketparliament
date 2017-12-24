package com.susheel.pocketparliament.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;
import com.susheel.pocketparliament.services.filters.FilterParameters;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * A simple {@link Fragment} subclass.
 */
public class MpOverviewFragment extends Fragment {


    private MemberParliament memberParliament;

    // Views
    private RecyclerView tweetsRecyclerView;

    public MpOverviewFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        memberParliament = (MemberParliament) args.getParcelable(FilterParameters.MEMBER_PARLIAMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mp_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bindViews(view);
        buildTweetsView();
    }

    private void bindViews(View view) {
        tweetsRecyclerView = (RecyclerView) view.findViewById(R.id.tweets_recycler_view);
    }

    private void buildTweetsView(){
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(memberParliament.getTwitterUsername())
                .maxItemsPerRequest(1)
                .includeRetweets(false)
                .build();

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightStyle)
                        .build();

        tweetsRecyclerView.setAdapter(adapter);
    }

    public static Fragment getForMemberParliament(MemberParliament memberParliament) {
        Fragment fragment = new MpOverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(FilterParameters.MEMBER_PARLIAMENT, memberParliament);
        fragment.setArguments(args);
        return fragment;
    }
}
