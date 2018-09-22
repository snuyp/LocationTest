package com.example.dima.locationtest.mvp.model.weather.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dima.locationtest.mvp.model.weather.db.DataCache;
import com.example.dima.locationtest.mvp.model.weather.db.WeatherData;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "myappname.db";
    private static final int DATABASE_VERSION = 1;

    private DataDao dataDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DataCache.class);
            TableUtils.createTable(connectionSource, WeatherData.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, DataCache.class, true);
            TableUtils.dropTable(connectionSource, WeatherData.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public DataDao getDataCashDAO() throws SQLException {
        if (dataDao == null) {
            dataDao = new DataDao(getConnectionSource());
        }
        return dataDao;
    }



    @Override
    public void close() {
        super.close();
        dataDao = null;
    }
}
