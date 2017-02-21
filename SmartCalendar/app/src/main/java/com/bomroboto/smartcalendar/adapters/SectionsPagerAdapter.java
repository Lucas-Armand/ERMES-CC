package com.bomroboto.smartcalendar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bomroboto.smartcalendar.fragments.CalendarFragment;
import com.bomroboto.smartcalendar.fragments.ContactsFragment;
import com.bomroboto.smartcalendar.fragments.EventsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position)
        {
            case 0:
                return CalendarFragment.newInstance();
            case 1:
                return EventsFragment.newInstance();
            case 2:
                return ContactsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        // Show 3 total pages.
        return 3;
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
                return ContactsFragment.TITLE;
        }
        return null;
    }
}