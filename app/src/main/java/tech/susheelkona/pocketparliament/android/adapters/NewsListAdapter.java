package tech.susheelkona.pocketparliament.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<NewsItem> list;
    private Context context;
    private RecyclerViewListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tag;
        private TextView description;
        private TextView date;

        private NewsItem newsItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tag = (TextView) itemView.findViewById(R.id.tag);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.news_date);

            itemView.setOnClickListener(this);
        }

        public void updateData(NewsItem newsItem) {
            this.newsItem = newsItem;
            tag.setText(newsItem.getTagline());
            description.setText(newsItem.getDescription());
            date.setText(newsItem.getDate().toUpperCase());
        }

        @Override
        public void onClick(View view) {
//            Log.i("CLICK", number.getText().toString());
            listener.onItemClick(newsItem);
        }
    }

    public NewsListAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        this.listener = new RecyclerViewListener() {
            @Override
            public void onItemClick(Object object) {
            }

            @Override
            public void onBottomReached() {

            }
        };
    }

    public void addRecyclerViewListener(RecyclerViewListener listener){
        this.listener = listener;
    }

    public void update(List<NewsItem> list){
        this.list.clear();
        notifyDataSetChanged();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void add(List<NewsItem> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_row, parent, false);
        return new NewsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateData(list.get(position));
        if (position == list.size() - 1){
            listener.onBottomReached();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
