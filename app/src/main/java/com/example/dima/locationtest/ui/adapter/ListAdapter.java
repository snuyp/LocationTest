package com.example.dima.locationtest.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.dima.locationtest.Interface.ItemClickListener;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.db.DataCash;

import java.util.List;


class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickListener itemClickListener;

    TextView city,coords,time;


    public ListViewHolder(View itemView) {
        super(itemView);
        city = itemView.findViewById(R.id.city);
        coords = itemView.findViewById(R.id.coords);
        time = itemView.findViewById(R.id.date);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        try {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        } catch (NullPointerException e) {
        }
    }
}

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private List<DataCash> articleList;
    private Context context;

    public ListAdapter(List<DataCash> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.card_layout, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {

                }
            });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
