package com.bomroboto.smartcalendar.data;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Andrei Benincasa on 27/02/2017.
 */

@Database(name = SmartCalendarDatabase.NAME, version = SmartCalendarDatabase.VERSION)
public class SmartCalendarDatabase extends BaseModel
{
    public static final String NAME = "SmartCalendarDatabase";
    public static final int VERSION = 1;
}
