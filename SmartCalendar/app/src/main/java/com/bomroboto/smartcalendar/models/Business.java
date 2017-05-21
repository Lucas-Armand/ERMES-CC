package com.bomroboto.smartcalendar.models;

import com.bomroboto.smartcalendar.data.SmartCalendarDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.joda.time.LocalTime;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

@Table(database = SmartCalendarDatabase.class)
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

    List<Service> services;

    List<Employee> employees;

    List<Customer> customers;

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

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "services")
    public List<Service> getServices() {
        if (services == null || services.isEmpty()) {
            services = SQLite.select()
                    .from(Service.class)
                    .where(Service_Table.business_id.eq(getId()))
                    .queryList();
        }

        return services;
    }

    public void setServices(List<Service> services) {
        if (services != null) {
            for (Service service : services) {
                service.setBusiness(this);
            }
            this.services = services;
        }
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "employees")
    public List<Employee> getEmployees() {
        if (employees == null || employees.isEmpty()) {
            employees = SQLite.select()
                    .from(Employee.class)
                    .where(Employee_Table.business_id.eq(getId()))
                    .queryList();
        }

        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        if (employees != null) {
            for (Employee employee : employees) {
                employee.setBusiness(this);
            }
        }
        this.employees = employees;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "customers")
    public List<Customer> getCustomers() {
        if (customers == null || customers.isEmpty()) {
            customers = SQLite.select()
                    .from(Customer.class)
                    .where(Customer_Table.business_id.eq(getId()))
                    .queryList();
        }

        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        if (customers != null) {
            for (Customer customer : customers) {
                customer.setBusiness(this);
            }
        }
        this.customers = customers;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "appointments")
    public List<Appointment> getAppointments() {
        if (appointments == null || appointments.isEmpty()) {
            appointments = SQLite.select()
                    .from(Appointment.class)
                    .where(Appointment_Table.business_id.eq(getId()))
                    .queryList();
        }

        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        if (appointments != null) {
            for (Appointment appointment : appointments) {
                appointment.setBusiness(this);
            }
        }
        this.appointments = appointments;
    }
}
