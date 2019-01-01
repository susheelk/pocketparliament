package tech.susheelkona.pocketparliament.android.fragments.votes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import tech.susheelkona.pocketparliament.android.tasks.GetVoteTask;
import tech.susheelkona.pocketparliament.services.filters.FilterParameters;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.android.tasks.AsyncResponseListener;
import tech.susheelkona.pocketparliament.model.legislation.Vote;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoteOverviewFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView title;
    private TextView description;
    private TextView votesFor;
    private TextView votesAgainst;
    private TextView resultText;
    private View content;

    private GetVoteTask task;


    public VoteOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vote_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        getData();

    }

    private void bindViews(View p) {
        progressBar = (ProgressBar) p.findViewById(R.id.progress_bar);
        title = (TextView) p.findViewById(R.id.vote_title);
        description = (TextView) p.findViewById(R.id.vote_description);
        votesAgainst = (TextView) p.findViewById(R.id.votes_against);
        votesFor = (TextView) p.findViewById(R.id.votes_for);
        resultText = (TextView) p.findViewById(R.id.result);
        content = p.findViewById(R.id.content);
        content.setVisibility(View.GONE);
    }

    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        task = new GetVoteTask();
        task.setAsyncResponseListener(new AsyncResponseListener<Vote>() {
            @Override
            public void onTaskSuccess(Class source, Vote data) {
                title.setText(data.getTitle());
                description.setText(data.getDescription());
                progressBar.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
                votesAgainst.setText(data.getNays()+" AGAINST");
                votesFor.setText("FOR "+data.getYeas()+"");
                String result = data.getResult();

                if(result.matches("Agreed to")){
                    resultText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    result = "passed";
                } else if (result.matches("Negatived")) {
                    resultText.setTextColor(getResources().getColor(R.color.senate));
                    result = "failed";
                }
                resultText.setText("VOTE "+result);

                if(data.getDescription().isEmpty()) {
                    description.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTaskError(Class source, String message) {
                Log.e("ERROR", message);
            }
        });
        task.execute(getArguments().getInt(FilterParameters.ID));
    }

    public static Fragment forVote(int id){
        Fragment fragment = new VoteOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FilterParameters.ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

}
