package com.bomroboto.smartcalendar.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.joda.time.LocalTime;

/**
 * Created by Andrei Benincasa on 18/05/2017.
 */

public class Schedule extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    DayOfWeek weekday;

    @Column
    LocalTime startWork;

    @Column
    LocalTime endWork;

    @Column
    LocalTime startBreak;

    @Column
    LocalTime endBreak;

    @Column
    boolean isAvailable;

    public Schedule()
    {
        super();
    }

    public Schedule(DayOfWeek weekday, LocalTime startWork, LocalTime endWork, LocalTime startBreak, LocalTime endBreak,
                    boolean isAvailable)
    {
        super();
        this.weekday = weekday;
        this.startWork = startWork;
        this.endWork = endWork;
        this.startBreak = startBreak;
        this.endBreak = endBreak;
        this.isAvailable = isAvailable;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DayOfWeek getWeekday()
    {
        return weekday;
    }

    public void setWeekday(DayOfWeek weekday)
    {
        this.weekday = weekday;
    }

    public LocalTime getStartWork()
    {
        return startWork;
    }

    public void setStartWork(LocalTime startWork)
    {
        this.startWork = startWork;
    }

    public LocalTime getEndWork()
    {
        return endWork;
    }

    public void setEndWork(LocalTime endWork)
    {
        this.endWork = endWork;
    }

    public LocalTime getStartBreak()
    {
        return startBreak;
    }

    public void setStartBreak(LocalTime startBreak)
    {
        this.startBreak = startBreak;
    }

    public LocalTime getEndBreak()
    {
        return endBreak;
    }

    public void setEndBreak(LocalTime endBreak)
    {
        this.endBreak = endBreak;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }
}
