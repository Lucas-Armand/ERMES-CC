package com.bomroboto.smartcalendar.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

public class Employee extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private
    List<Schedule> schedules;

    public Employee()
    {
        super();
    }

    public Employee(String name, String surname, List<Schedule> schedules)
    {
        super();
        this.name = name;
        this.surname = surname;
        this.schedules = schedules;
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

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public List<Schedule> getSchedules()
    {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedule)
    {
        this.schedules = schedule;
    }
}
