package com.susheel.pocketparliament.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.model.legislation.Vote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VotesListAdapter extends RecyclerView.Adapter<VotesListAdapter.ViewHolder> {

    private List<Vote> data;
    private Context context;
    private RecyclerViewListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView id;
        private TextView name;
        private TextView date;
        private TextView result;
        private TextView count;
        private View layout;

        private Vote vote;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.vote_id);
            name = (TextView) itemView.findViewById(R.id.vote_title);
            date = (TextView) itemView.findViewById(R.id.vote_date);
            result = (TextView) itemView.findViewById(R.id.result);
            result.setAllCaps(true);
            count = (TextView) itemView.findViewById(R.id.count);
            layout = itemView;
            itemView.setOnClickListener(this);
        }

        public void updateData(Vote vote) {
            this.vote = vote;
            id.setText(vote.getId()+"");
            name.setText(vote.getTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            date.setText(dateFormat.format(vote.getDate()));
            date.setAllCaps(true);
            result.setText(vote.getResult()+":");

            if(vote.getResult().matches("Agreed To")){
                layout.setBackgroundColor(context.getResources().getColor(R.color.positive));
                result.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                id.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (vote.getResult().matches("Negatived")){
                layout.setBackgroundColor(context.getResources().getColor(R.color.negative));
                id.setTextColor(context.getResources().getColor(R.color.senate));
            }

            count.setText(vote.getYeas()+" for, "+vote.getNays()+" against");
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(vote);
        }
    }

    public VotesListAdapter() {
    }

    public VotesListAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.listener = new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {

            }
        };
    }

    public void addRecyclerViewListener(RecyclerViewListener listener){
        this.listener = listener;
    }

    public void update(List<Vote> data) {
        this.data.clear();
        notifyDataSetChanged();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.votes_list_row, parent, false);
        return new VotesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
