package com.example.dima.locationtest.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.locationtest.mvp.model.weather.dao.HelperFactory;
import com.example.dima.locationtest.mvp.model.weather.db.DataCash;
import com.example.dima.locationtest.mvp.view.CashView;

import java.sql.SQLException;
import java.util.List;

@InjectViewState
public class CashPresenter extends MvpPresenter<CashView> {

    public void getCash()
    {
        try {
           List<DataCash> dataCashes =  HelperFactory.getHelper().getDataCashDAO().getAll();
           getViewState().weatherView(dataCashes);
        } catch (SQLException e) {
            e.printStackTrace();
            getViewState().error();
        }

    }
}