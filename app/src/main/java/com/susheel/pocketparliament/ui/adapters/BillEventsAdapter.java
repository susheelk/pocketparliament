package com.susheel.pocketparliament.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;
import com.susheel.pocketparliament.model.legislation.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillEventsAdapter extends RecyclerView.Adapter<BillEventsAdapter.ViewHolder> {
    private List<Event> list;
    private Context context;

    public BillEventsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public BillEventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_events_row, parent, false);
        return new BillEventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateData(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<Event> list){
        this.list.clear();
        notifyDataSetChanged();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView date;
        private TextView status;
        private TextView chamber;

        public ViewHolder(View it) {
            super(it);
            date = (TextView)it.findViewById(R.id.bill_date);
            status = (TextView)it.findViewById(R.id.event);
            chamber = (TextView) it.findViewById(R.id.chamber);
        }

        public void updateData(Event event) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            date.setText(dateFormat.format(event.getDate()));
            date.setAllCaps(true);
            status.setText(event.getStatus());
            chamber.setText(event.getChamber());

            if(event.getChamber().matches("Senate")){
                date.setTextColor(context.getResources().getColor(R.color.senate));
            } else if(event.getChamber().matches("House of Commons")){
                date.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
        }
    }
}
