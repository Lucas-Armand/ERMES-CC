package com.bomroboto.smartcalendar;

import android.app.Application;
import android.content.res.Configuration;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Andrei Benincasa on 14/02/2017.
 * andrei.benincasa@gmail.com
 */
public class SmartCalendar extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }
}
