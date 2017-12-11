package com.susheel.pocketparliament.ui.adapters;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susheel.pocketparliament.R;
import com.susheel.pocketparliament.model.MemberParliament;

import java.util.List;

/**
 * @author Susheel Kona
 */

public class MpListAdapter extends RecyclerView.Adapter<MpListAdapter.ViewHolder> {

    private List<MemberParliament> list;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name; // VERY bad practice TODO fix
        public TextView description;
        public SimpleDraweeView image;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            image = (SimpleDraweeView) view.findViewById(R.id.drawee);
        }
    }

    public MpListAdapter(List<MemberParliament> list, Context context) {
        this.list = list;
        this.context = context;
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
        holder.description.setText(item.getBlurb());
        holder.image.setImageURI(item.getImageUrl());
        holder.image.getHierarchy().setActualImageFocusPoint(new PointF(0.5f, 0.35f));
//        Glide.with(context).load("http://i.imgur.com/DvpvklR.png");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
