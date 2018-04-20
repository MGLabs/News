package com.mglabs.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mglabs.news.model.Article;
import com.mglabs.news.utils.DateUtils;

import java.util.List;

/**
 * Created by mgd on 24/01/18.
 */

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.HomeNewsViewHolder> {

    private List<Article> newsArticles;

    //costruttore dell'adapter
    public HomeNewsAdapter(List<Article> newsArticles) {
        this.newsArticles = newsArticles;
    }


    @Override
    public HomeNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news, parent, false);
        HomeNewsViewHolder homeNewsViewHolder = new HomeNewsViewHolder(view);
        return homeNewsViewHolder;
    }

    @Override  //responsible for updating a given empty view with the corresponding data
    public void onBindViewHolder(HomeNewsViewHolder holder, final int position) {
        Article newsArticle = newsArticles.get(position);
        Glide.with(holder.cardImageView.getContext()).load(newsArticle.getUrlToImage())
                .centerCrop()
                .into(holder.cardImageView );
        holder.cardTitletextView.setText(newsArticle.getTitle());
        holder.cardTimeTextView.setText(DateUtils.formatNewsApiDate(newsArticle.getPublishedAt()));
        holder.cardContentTextView.setText(newsArticle.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NewsDetailActivity.launch(v.getContext(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsArticles.size();     //ricorda di fare un check per null pointer when in production
    }

    public void clear() {
        newsArticles.clear();
        notifyDataSetChanged();
    }


    //costruttore del viewHolder
    public static class HomeNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImageView;
        TextView cardTitletextView;
        TextView cardTimeTextView;
        TextView cardContentTextView;


        public HomeNewsViewHolder(View itemView) {
            super(itemView);

            cardImageView = (ImageView)itemView.findViewById(R.id.card_news_image);
            cardTitletextView = itemView.findViewById(R.id.card_news_title);
            cardTimeTextView = (TextView) itemView.findViewById(R.id.card_news_time);
            cardContentTextView = (TextView) itemView.findViewById(R.id.card_news_content);
        }
    }



}



