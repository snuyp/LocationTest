package com.example.dima.locationtest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.dima.locationtest.R;
import com.example.dima.locationtest.mvp.model.weather.db.DataCache;
import com.example.dima.locationtest.mvp.presenter.CashPresenter;
import com.example.dima.locationtest.mvp.view.CashView;
import com.example.dima.locationtest.ui.adapter.ListAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class DataFragment extends MvpAppCompatFragment implements CashView {
    @InjectPresenter
    CashPresenter cashPresenter;

    private RecyclerView lstNews;
    private RecyclerView.LayoutManager layoutManager;
    private ListAdapter adapter;

    public static DataFragment fragment;

    public static DataFragment getInstance() {
        if (fragment == null) {
            fragment = new DataFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_data,container,false);

        lstNews = v.findViewById(R.id.recycler_view);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        lstNews.setLayoutManager(layoutManager);

        cashPresenter.getCash();
        return v;

    }

    @Override
    public void weatherView(List<DataCache> cashList) {
        adapter = new ListAdapter(cashList);
        lstNews.setAdapter(adapter);
    }

    @Override
    public void error() {
        Toasty.error(getContext(),"Error").show();
    }
}
