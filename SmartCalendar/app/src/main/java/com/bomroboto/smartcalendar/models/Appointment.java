package com.bomroboto.smartcalendar.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

public class Appointment extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    LocalDate date;

    @Column
    LocalTime time;

    @Column
    Service service;

    @Column
    Customer customer;

    @Column
    Employee employee;

    @Column
    String status;

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
}
