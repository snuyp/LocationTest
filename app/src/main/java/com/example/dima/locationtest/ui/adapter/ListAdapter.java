package com.example.dima.locationtest.ui.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.dima.locationtest.Interface.ItemClickListener;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.dao.HelperFactory;
import com.example.dima.locationtest.mvp.model.weather.db.DataCache;
import com.example.dima.locationtest.ui.WeatherActivity;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
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
        itemView.setOnLongClickListener(this);
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

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private List<DataCache> cashList;

    public ListAdapter(List<DataCache> cashList) {
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
                public void onClick(View view, final int position, boolean isLongClick) {
                    if(!isLongClick) {
                        Intent intent = new Intent(holder.itemView.getContext(), WeatherActivity.class);
                        intent.putExtra("weather", cashList.get(position));
                        holder.itemView.getContext().startActivity(intent);
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle(R.string.sure_delete)
                                .setCancelable(true)
                                .setPositiveButton(R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                try {
                                                    HelperFactory.getHelper().getDataCashDAO().delete(cashList.get(position));
                                                    cashList.remove(holder.getAdapterPosition());
                                                    notifyItemRemoved(holder.getAdapterPosition());
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }

                                                dialog.cancel();
                                            }
                                        })
                                .setNegativeButton(R.string.return_dialog,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return cashList.size();
    }
}
