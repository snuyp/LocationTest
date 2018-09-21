package com.example.dima.locationtest.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.dima.locationtest.Interface.ItemClickListener;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.db.DataCash;
import com.example.dima.locationtest.ui.WeatherActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickListener itemClickListener;

    TextView city,coords,time;
    ImageView imageView;
    SimpleDateFormat sdf = new SimpleDateFormat("E dd.MM.yyyy '[' HH:mm:ss ']'" ,new Locale("en","US"));

    public ListViewHolder(View itemView) {
        super(itemView);
        city = itemView.findViewById(R.id.city);
        coords = itemView.findViewById(R.id.coords);
        time = itemView.findViewById(R.id.date);
        imageView = itemView.findViewById(R.id.image);
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
    private List<DataCash> cashList;

    public ListAdapter(List<DataCash> cashList) {
        this.cashList = cashList;
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
        holder.city.setText(cashList.get(position).getWeatherData().getCity());
        String coords = cashList.get(position).getLatitude()+" / "+ cashList.get(position).getLongitude();
        holder.coords.setText("["+coords+"]");
        String date =  holder.sdf.format(cashList.get(position).getDate());
        holder.time.setText(date);

        Glide.with(holder.itemView.getContext())
                .load(cashList.get(position).getWeatherData().getIcon())
                .into(holder.imageView);

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent = new Intent(holder.itemView.getContext(), WeatherActivity.class);
                    intent.putExtra("weather", cashList.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return cashList.size();
    }
}
