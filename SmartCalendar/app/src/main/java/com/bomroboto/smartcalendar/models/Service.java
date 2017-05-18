package com.bomroboto.smartcalendar.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

public class Service extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    String name;

    @Column
    String description;

    @Column
    int duration;

    @Column
    double price = 0.0;

    public Service()
    {
        super();
    }

    public Service(String name, String description, int duration, double price)
    {
        super();
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}
