package com.example.dima.locationtest.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.example.dima.locationtest.mvp.model.weather.db.DataCash;

import java.util.List;

public interface CashView extends MvpView {
    void weatherView(List<DataCash> cashList);
    void error();
}
