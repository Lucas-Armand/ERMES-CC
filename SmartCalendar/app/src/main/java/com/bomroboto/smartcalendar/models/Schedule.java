package com.bomroboto.smartcalendar.models;

import com.bomroboto.smartcalendar.data.SmartCalendarDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.joda.time.LocalTime;

import java.io.Serializable;

/**
 * Created by Andrei Benincasa on 18/05/2017.
 */

@Table(database = SmartCalendarDatabase.class)
public class Schedule extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private DayOfWeek weekday;

    @Column
    private LocalTime startWork;

    @Column
    private LocalTime endWork;

    @Column
    private LocalTime startBreak;

    @Column
    private LocalTime endBreak;

    @Column
    boolean isAvailable;

    @ForeignKey(stubbedRelationship = true)
    private Employee employee;

    public Schedule() {
        super();
    }

    public Schedule(DayOfWeek weekday, LocalTime startWork, LocalTime endWork, LocalTime startBreak, LocalTime endBreak,
                    boolean isAvailable) {
        super();
        this.weekday = weekday;
        this.startWork = startWork;
        this.endWork = endWork;
        this.startBreak = startBreak;
        this.endBreak = endBreak;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getWeekday() {
        return weekday;
    }

    public void setWeekday(DayOfWeek weekday) {
        this.weekday = weekday;
    }

    public LocalTime getStartWork() {
        return startWork;
    }

    public void setStartWork(LocalTime startWork) {
        this.startWork = startWork;
    }

    public LocalTime getEndWork() {
        return endWork;
    }

    public void setEndWork(LocalTime endWork) {
        this.endWork = endWork;
    }

    public LocalTime getStartBreak() {
        return startBreak;
    }

    public void setStartBreak(LocalTime startBreak) {
        this.startBreak = startBreak;
    }

    public LocalTime getEndBreak() {
        return endBreak;
    }

    public void setEndBreak(LocalTime endBreak) {
        this.endBreak = endBreak;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Employee getEmployee() {
        if (employee == null) {
            employee = SQLite.select()
                    .from(Employee.class)
                    .where(Employee_Table.business_id.eq(getId()))
                    .querySingle();
        }

        return employee;
    }

    public void setEmployee(Employee employee) {
        if (employee != null) {
            this.employee = employee;
        }
    }
}
