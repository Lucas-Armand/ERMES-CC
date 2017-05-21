package com.bomroboto.smartcalendar.models;

import com.bomroboto.smartcalendar.data.SmartCalendarDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

@Table(database = SmartCalendarDatabase.class)
public class Appointment extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

    @Column
    @ForeignKey
    private Service service;

    @Column
    @ForeignKey
    private Customer customer;

    @Column
    @ForeignKey
    private Employee employee;

    @Column
    private String status;

    @ForeignKey(stubbedRelationship = true)
    private Business business;

    public Appointment() {
        super();
    }

    public Appointment(LocalDate date, LocalTime time, Service service, Customer customer, Employee employee, String status) {
        super();
        this.date = date;
        this.time = time;
        this.service = service;
        this.customer = customer;
        this.employee = employee;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
