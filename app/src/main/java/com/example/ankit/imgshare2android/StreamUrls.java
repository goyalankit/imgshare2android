package com.example.ankit.imgshare2android;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ankit on 10/29/14.
 */
public class StreamUrls {

    @SerializedName("url")
    public String url;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("stream_id")
    public String streamId;

    @SerializedName("location")
    public ArrayList<Double> location;

}
