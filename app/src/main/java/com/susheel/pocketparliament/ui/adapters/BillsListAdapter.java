package com.susheel.pocketparliament.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.legislation.Bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillsListAdapter extends RecyclerView.Adapter<BillsListAdapter.ViewHolder> {

    private List<Bill> list;
    private Context context;
    private RecyclerViewListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView number;
        private TextView title;
        private TextView date;
        private TextView event;

        private Bill bill;

        public ViewHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.bill_number);
            title = (TextView) itemView.findViewById(R.id.bill_title);
            date = (TextView) itemView.findViewById(R.id.bill_date);
            event = (TextView) itemView.findViewById(R.id.event);

            itemView.setOnClickListener(this);
        }

        public void updateData(Bill bill) {
            this.bill = bill;
            number.setText(bill.getNumber());
            if(bill.getNumber().startsWith("S")){
                number.setTextColor(context.getResources().getColor(R.color.senate));
                System.out.println(bill.getNumber()+" red");
            } else {
                number.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

            title.setText(bill.getTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            date.setText(dateFormat.format(bill.getLastMajorEvent().getDate()));
            date.setAllCaps(true);
            event.setText(bill.getLastMajorEvent().getStatus());
        }

        @Override
        public void onClick(View view) {
            Log.i("CLICK", number.getText().toString());
            listener.onItemClick(bill);
        }
    }

    public BillsListAdapter(Context context) {
        this.list = new ArrayList<>();
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

    public void update(List<Bill> list){
        this.list.clear();
        notifyDataSetChanged();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_row, parent, false);
        return new BillsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
