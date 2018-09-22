package com.example.dima.locationtest.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.example.dima.locationtest.mvp.model.weather.db.DataCache;

import java.util.List;

public interface CashView extends MvpView {
    void weatherView(List<DataCache> cashList);
    void error();
}
