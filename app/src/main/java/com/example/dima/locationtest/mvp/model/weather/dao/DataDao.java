package com.example.dima.locationtest.mvp.model.weather.dao;

import com.example.dima.locationtest.mvp.model.weather.db.DataCash;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class DataDao extends BaseDaoImpl<DataCash,Long> {

    DataDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource,DataCash.class);
    }

    public List<DataCash> findByName(String name) throws SQLException {
        return super.queryForEq("name", name);
    }
    public List<DataCash> getAll() throws SQLException {
        return this.queryForAll();
    }
}
