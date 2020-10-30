package com.ews.workdaytest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingDataModel {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String RATING = "rating";
    public static final String SOURCE = "source_tld";

    @Expose
    @SerializedName(ID)
    public String id;

    @Expose
    @SerializedName(TITLE)
    public String title;


    @Expose
    @SerializedName(RATING)
    public String rating;

    @Expose
    @SerializedName(SOURCE)
    public String source;
}
