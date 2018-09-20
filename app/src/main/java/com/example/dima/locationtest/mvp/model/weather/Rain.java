package com.example.dima.locationtest.mvp.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rain {
    @SerializedName("3h")
    @Expose
    private Double _3h;

    public Rain(Double _3h) {
        this._3h = _3h;
    }

    public Double get_3h() {
        return _3h;
    }

    public void set_3h(Double _3h) {
        this._3h = _3h;
    }
}
