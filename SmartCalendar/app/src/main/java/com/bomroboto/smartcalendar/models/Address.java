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
 * Created by Andrei Benincasa on 18/05/2017.
 */

@Table(database = SmartCalendarDatabase.class)
public class Address extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    String street;

    @Column
    int number;

    @Column
    String locality;

    @Column
    String city;

    @ForeignKey(stubbedRelationship = true)
    private Customer customer;

    public Address() {
        super();
    }

    public Address(String street, int number, String locality, String city) {
        super();
        this.street = street;
        this.number = number;
        this.locality = locality;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Customer getCustomer() {
        if (customer == null) {
            customer = SQLite.select()
                    .from(Customer.class)
                    .where(Customer_Table.address_id.eq(getId()))
                    .querySingle();
        }

        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer != null) {
            this.customer = customer;
        }
    }
}
