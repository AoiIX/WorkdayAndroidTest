package com.ews.workdaytest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TrendingModel implements Serializable {
    public static final String DATA = "data";

    @Expose
    @SerializedName(DATA)
    public ArrayList<TrendingDataModel> data;
}