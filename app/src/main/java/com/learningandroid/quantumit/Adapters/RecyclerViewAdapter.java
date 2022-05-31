package com.learningandroid.quantumit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learningandroid.quantumit.R;
import com.learningandroid.quantumit.model.News;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<News> newsList;
    Context context;

    public RecyclerViewAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).getTitle());
        holder.content.setText(newsList.get(position).getDescription());
        holder.source.setText(newsList.get(position).getSource());
        String imageurl = newsList.get(position).getUrlImage();
        Picasso.get()
                .load(imageurl)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView content;
        TextView publishedAt;
        TextView source;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_imageView);
            title = itemView.findViewById(R.id.newsTitleTextView);
            content = itemView.findViewById(R.id.newsContentTextView);
            publishedAt = itemView.findViewById(R.id.timeagoTextview);
            source = itemView.findViewById(R.id.sourceTextView);
        }
    }
}