package com.bomroboto.smartcalendar.utils;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import org.joda.time.LocalDate;

/**
 * Created by Andrei Benincasa on 18/05/2017.
 */

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class LocalDateConverter extends TypeConverter<String, LocalDate> {
    @Override
    public String getDBValue(LocalDate model) {
        return model == null ? null : model.toString();
    }

    @Override
    public LocalDate getModelValue(String data) {
        return data == null ? null : new LocalDate(data);
    }
}
