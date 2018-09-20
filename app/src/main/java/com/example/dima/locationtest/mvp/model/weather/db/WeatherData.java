package com.example.dima.locationtest.mvp.model.weather.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.dima.locationtest.mvp.model.weather.db.WeatherData.WEATHER_DATA_FIELD_NAME;

@DatabaseTable(tableName = WEATHER_DATA_FIELD_NAME)
public class WeatherData {
    public final static String WEATHER_DATA_FIELD_NAME = "weather_data";

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    private DataCash data;

    @DatabaseField
    private String city;

    @DatabaseField
    private String temp;

    @DatabaseField
    private String windSpeed;

    @DatabaseField
    private String humidity;

    @DatabaseField
    private String pressure;

    @DatabaseField
    private String sunset;

    @DatabaseField
    private String sunrise;

    @DatabaseField
    private String icon;

    public WeatherData() {

    }

    public WeatherData(String city, String temp, String windSpeed, String humidity, String pressure, String sunset, String sunrise, String icon) {
        this.city = city;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.icon = icon;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public DataCash getData() {
        return data;
    }

    public void setData(DataCash data) {
        this.data = data;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
