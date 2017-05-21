package com.bomroboto.smartcalendar.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bomroboto.smartcalendar.views.fragments.CalendarFragment;
import com.bomroboto.smartcalendar.views.fragments.ContactsFragment;
import com.bomroboto.smartcalendar.views.fragments.CustomersFragment;
import com.bomroboto.smartcalendar.views.fragments.EventsFragment;

public class PagerAdapter extends FragmentPagerAdapter
{
    private final Fragment[] fragments =
            {
                    CalendarFragment.newInstance(),
                    EventsFragment.newInstance(),
                    CustomersFragment.newInstance()
            };

    public PagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments[position];
    }

    @Override
    public int getCount()
    {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return CalendarFragment.TITLE;
            case 1:
                return EventsFragment.TITLE;
            case 2:
                return CustomersFragment.TITLE;
        }

        return null;
    }
}