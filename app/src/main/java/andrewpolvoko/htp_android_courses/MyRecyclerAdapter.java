package andrewpolvoko.htp_android_courses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.RssItemViewHolder> {

    private final ArrayList<NewsItem> mData = new ArrayList<>();


    @Override
    public RssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RssItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {

        NewsItem newsItem = mData.get(position);
        holder.title.setText(newsItem.title);
        holder.description.setText(newsItem.description);
        Glide.with(holder.imageView.getContext()).load(newsItem.imageLink).into(holder.imageView);

    }

    public NewsItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<NewsItem> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public static class RssItemViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView description;
        final ImageView imageView;

        public RssItemViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }

    }


}
