package com.bomroboto.smartcalendar.models;

import com.bomroboto.smartcalendar.data.SmartCalendarDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by Andrei Benincasa on 17/05/2017.
 */

@Table(database = SmartCalendarDatabase.class)
public class Customer extends BaseModel implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    @ForeignKey(saveForeignKeyModel = true)
    private Address address;

    @ForeignKey(stubbedRelationship = true)
    private Business business;

    public Customer() {
        super();
    }

    public Customer(String name, String surname, String phone, String email, Address address) {
        super();
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        if (address == null) {
            address = SQLite.select()
                    .from(Address.class)
                    .where(Address_Table.customer_id.eq(getId()))
                    .querySingle();
        }

        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
