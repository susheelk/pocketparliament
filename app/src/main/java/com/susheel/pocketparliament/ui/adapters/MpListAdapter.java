package com.susheel.pocketparliament.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;

import java.util.List;

/**
 * @author Susheel Kona
 */

public class MpListAdapter extends RecyclerView.Adapter<MpListAdapter.ViewHolder> {

    private List<MemberParliament> list;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public MpListAdapter(List<MemberParliament> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mp_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MemberParliament item = list.get(position);
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
