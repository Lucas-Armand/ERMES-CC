package com.bomroboto.smartcalendar.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.joda.time.LocalTime;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

public class Business extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private String name;

    @Column
    LocalTime openingTime;

    @Column
    LocalTime closingTime;

    @Column
    String fieldOfActivity;

    @Column
    List<Service> services;

    @Column
    List<Employee> employees;

    @Column
    List<Customer> customers;

    @Column
    List<Appointment> appointments;

    public Business() {
        super();
    }

    public Business(String name, LocalTime openingTime, LocalTime closingTime, String fieldOfActivity) {
        super();
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.fieldOfActivity = fieldOfActivity;
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

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getFieldOfActivity() {
        return fieldOfActivity;
    }

    public void setFieldOfActivity(String fieldOfActivity) {
        this.fieldOfActivity = fieldOfActivity;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
