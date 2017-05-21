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
public class Service extends BaseModel implements Serializable {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column
    String name;

    @Column
    String description;

    @Column
    int duration;

    @Column
    double price = 0.0;

    @ForeignKey(stubbedRelationship = true)
    private Business business;

    public Service() {
        super();
    }

    public Service(String name, String description, int duration, double price) {
        super();
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
