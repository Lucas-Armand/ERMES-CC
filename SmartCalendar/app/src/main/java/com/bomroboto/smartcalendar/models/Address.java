package com.bomroboto.smartcalendar.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;

/**
 * Created by Andrei Benincasa on 18/05/2017.
 */

public class Address {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    String street;

    @Column
    int number;

    @Column
    String locality;

    @Column
    String city;

    public Address()
    {
        super();
    }

    public Address(String street, int number, String locality, String city)
    {
        super();
        this.street = street;
        this.number = number;
        this.locality = locality;
        this.city = city;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getLocality()
    {
        return locality;
    }

    public void setLocality(String locality)
    {
        this.locality = locality;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
}
