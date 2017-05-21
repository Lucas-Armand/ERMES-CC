package com.bomroboto.smartcalendar.models;

import com.bomroboto.smartcalendar.data.SmartCalendarDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

@Table(database = SmartCalendarDatabase.class)
public class Employee extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    List<Schedule> schedules;

    @ForeignKey(stubbedRelationship = true)
    private Business business;

    public Employee() {
        super();
    }

    public Employee(String name, String surname, ArrayList<Schedule> schedules) {
        super();
        this.name = name;
        this.surname = surname;
        this.schedules = schedules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "schedules")
    public List<Schedule> getSchedules() {
        if (schedules == null || schedules.isEmpty()) {
            schedules = SQLite.select()
                    .from(Schedule.class)
                    .where(Schedule_Table.employee_id.eq(getId()))
                    .queryList();
        }

        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        if (schedules != null) {
            this.schedules = schedules;
        }
    }

    public Business getBusiness() {
        if (business == null) {
            business = SQLite.select()
                    .from(Business.class)
                    .where(Service_Table.business_id.eq(getId()))
                    .querySingle();
        }

        return business;
    }

    public void setBusiness(Business business) {
        if (business != null) {
            this.business = business;
        }
    }
}
