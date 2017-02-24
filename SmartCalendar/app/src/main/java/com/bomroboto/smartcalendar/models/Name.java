package com.bomroboto.smartcalendar.models;

import com.google.gson.annotations.SerializedName;

public class Name
{
    @SerializedName("title")
    String title;

    @SerializedName("first")
    String first;

    @SerializedName("last")
    String last;

    public String getTitle()
    {
        return title;
    }

    public String getFirst()
    {
        return first;
    }

    public String getLast()
    {
        return last;
    }
}
