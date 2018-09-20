package com.example.dima.locationtest.mvp.model.weather.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.dima.locationtest.mvp.model.weather.db.DataCash.DATA_FIELD_NAME;

@DatabaseTable(tableName = DATA_FIELD_NAME, daoClass = DataCash.class)
public class DataCash {
    public final static String DATA_FIELD_NAME = "data";

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private double latitude;
    @DatabaseField
    private double longitude;

    @DatabaseField(foreign = true)
    private WeatherData weatherData;
    @DatabaseField(dataType = DataType.DATE)
    private Date date;


    public DataCash() {
        date = new Date();
    }

    public DataCash(double latitude, double longitude, WeatherData weatherData) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherData = weatherData;
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }
}
