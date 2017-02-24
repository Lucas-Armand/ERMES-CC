package com.bomroboto.smartcalendar.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrei Benincasa on 22/02/2017.
 * andrei.benincasa@gmail.com
 */
public class Contact
{
    @SerializedName("name")
    Name name;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("picture")
    Picture picture;

    public Contact(Name name, String email, String phone, Picture picture)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
    }

    public Name getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public Picture getPicture()
    {
        return picture;
    }
}

