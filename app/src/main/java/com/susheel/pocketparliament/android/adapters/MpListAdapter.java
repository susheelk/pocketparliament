package com.susheel.pocketparliament.android.adapters;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Susheel Kona
 */

public class MpListAdapter extends RecyclerView.Adapter<MpListAdapter.ViewHolder> {

    private List<MemberParliament> list;
    private Context context;
    private RecyclerViewListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name; // VERY bad practice TODO fix
        public TextView description;
        public SimpleDraweeView image;
        private MemberParliament item;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            image = (SimpleDraweeView) view.findViewById(R.id.drawee);

            view.setOnClickListener(this);
        }

        void updateData(MemberParliament memberParliament) {
            this.item = memberParliament;
            name.setText(item.getName());
            description.setText(item.getBlurb());

            image.setImageURI(item.getImageUrl());
            image.getHierarchy().setActualImageFocusPoint(new PointF(0.5f, 0.35f));
        }


        @Override
        public void onClick(View v) {
            Log.i("Click", "CLICK");
            listener.onItemClick(item);
        }
    }

    public MpListAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        this.listener = new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {

            }
        };
    }

    public void update(List<MemberParliament> list) {
        this.list.clear();
        notifyDataSetChanged();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addRecyclerViewListener(RecyclerViewListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mp_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MemberParliament item = list.get(position);
        holder.updateData(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
