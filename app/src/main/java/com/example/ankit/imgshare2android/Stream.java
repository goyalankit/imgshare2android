package com.example.ankit.imgshare2android;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ankit on 10/26/14.
 */
public class Stream {
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("count")
    public int numPictures;

    @SerializedName("view_count")
    public int numViews;

    @SerializedName("tags")
    public List<String> tags;

    @SerializedName("cover_image")
    public String coverImageUrl;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String upadatedAt;

}
