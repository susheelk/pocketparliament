package tech.susheelkona.pocketparliament.android.fragments.mps;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import tech.susheelkona.pocketparliament.model.MemberParliament;
import tech.susheelkona.pocketparliament.services.filters.FilterParameters;
import tech.susheelkona.pocketparliament.R;

import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * A simple {@link Fragment} subclass.
 */
public class MpOverviewFragment extends Fragment {


    private MemberParliament memberParliament;

    // Views
    private RecyclerView tweetsRecyclerView;
    private Button callButton;
    private Button emailButton;
    private Button websiteButton;
    private Button parlButton;

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
        attachButtonClickListeners();
    }

    private void bindViews(View view) {
        tweetsRecyclerView = (RecyclerView) view.findViewById(R.id.tweets_recycler_view);
        callButton = (Button) view.findViewById(R.id.call_button);
        emailButton = (Button) view.findViewById(R.id.email_button);
        websiteButton = (Button) view.findViewById(R.id.website_button);
        parlButton = (Button) view.findViewById(R.id.parl_site_button);
    }

    private void buildTweetsView(){
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

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

    private void attachButtonClickListeners() {
        ButtonClickListener listener = new ButtonClickListener();
        callButton.setOnClickListener(listener);
        emailButton.setOnClickListener(listener);
        websiteButton.setOnClickListener(listener);
        parlButton.setOnClickListener(listener);
    }

    public static Fragment getForMemberParliament(MemberParliament memberParliament) {
        Fragment fragment = new MpOverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(FilterParameters.MEMBER_PARLIAMENT, memberParliament);
        fragment.setArguments(args);
        return fragment;
    }

    class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            try {
                if (v.equals(callButton)) {
                    startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+memberParliament.getPhoneNumber())));
                } else if (v.equals(emailButton)) {
                    String mailto = "mailto:"+memberParliament.getEmailAddress()+"?body="+Uri.encode("\nSent from the PocketParliament App");
                    startActivity(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse(mailto)));
                } else if (v.equals(websiteButton)) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(memberParliament.getPersonalUrl())));
                } else if (v.equals(parlButton)) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(memberParliament.getParlUrl())));
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No Suitable app found for action", Toast.LENGTH_LONG);
            }
        }
    }

}
