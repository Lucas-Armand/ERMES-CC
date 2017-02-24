package com.bomroboto.smartcalendar.models;

import com.google.gson.annotations.SerializedName;

public class Picture
{
    @SerializedName("large")
    String large;

    @SerializedName("medium")
    String medium;

    @SerializedName("thumbnail")
    String thumbnail;

    public Picture(String large, String medium, String thumbnail)
    {
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    public String getLarge()
    {
        return large;
    }

    public String getMedium()
    {
        return medium;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }
}
