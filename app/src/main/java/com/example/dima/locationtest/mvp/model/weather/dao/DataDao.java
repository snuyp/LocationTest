package com.example.dima.locationtest.mvp.model.weather.dao;

import com.example.dima.locationtest.mvp.model.weather.db.DataCache;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class DataDao extends BaseDaoImpl<DataCache,Long> {

    DataDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource,DataCache.class);
    }

    public List<DataCache> findByName(String name) throws SQLException {
        return super.queryForEq("name", name);
    }
    public List<DataCache> getAll() throws SQLException {
        return this.queryForAll();
    }
}
