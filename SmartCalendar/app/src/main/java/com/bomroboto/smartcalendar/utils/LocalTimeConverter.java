package com.bomroboto.smartcalendar.utils;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import org.joda.time.LocalTime;

/**
 * Created by Andrei Benincasa on 18/05/2017.
 */

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class LocalTimeConverter extends TypeConverter<String, LocalTime> {
    @Override
    public String getDBValue(LocalTime model) {
        return model == null ? null : model.toString();
    }

    @Override
    public LocalTime getModelValue(String data) {
        return data == null ? null : new LocalTime(data);
    }
}
