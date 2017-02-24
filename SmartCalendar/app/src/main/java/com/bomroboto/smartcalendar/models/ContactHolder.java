package com.bomroboto.smartcalendar.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Andrei Benincasa on 23/02/2017.
 * andrei.benincasa@gmail.com
 */
public class ContactHolder
{
    @SerializedName("results")
    ArrayList<Contact> contacts;

    public ArrayList<Contact> getContacts()
    {
        return contacts;
    }
}
