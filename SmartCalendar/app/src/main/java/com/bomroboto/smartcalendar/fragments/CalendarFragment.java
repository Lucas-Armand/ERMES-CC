package com.bomroboto.smartcalendar.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bomroboto.smartcalendar.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Calendar;

public class CalendarFragment extends Fragment
{
    public static String TITLE = "Calendário";

    MaterialCalendarView materialCalendarView;

    public CalendarFragment()
    {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance()
    {
        return new CalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        materialCalendarView = (MaterialCalendarView) root.findViewById(R.id.calendarView);

        Calendar today = Calendar.getInstance();
        materialCalendarView.setSelectedDate(today.getTime());

        Calendar firstDayOfTheYear = Calendar.getInstance();
        firstDayOfTheYear.set(firstDayOfTheYear.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar lastDayOfTheYear = Calendar.getInstance();
        lastDayOfTheYear.set(lastDayOfTheYear.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        materialCalendarView.state().edit()
                .setMinimumDate(firstDayOfTheYear.getTime())
                .setMaximumDate(lastDayOfTheYear.getTime())
                .commit();

        materialCalendarView.addDecorator(new MarkTodayDecorator((Activity) getContext()));

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_calendar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_today)
        {
            Calendar today = Calendar.getInstance();
            materialCalendarView.setSelectedDate(today.getTime());
            materialCalendarView.setCurrentDate(today.getTime());
        }

        return super.onOptionsItemSelected(item);
    }
}

/**
 * It's okay to leave MarkTodayDecorator here (and not in a specific package) for now. After all,
 * MarkTodayDecorator will only be used by CalendarFragment.
 */

class MarkTodayDecorator implements DayViewDecorator
{
    int color;

    public MarkTodayDecorator(Activity context)
    {
        // get the primary color of the style sheet
        color = context.getResources().getColor(R.color.primary);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day)
    {
        // if day is the current day, decorate
        return day.equals(CalendarDay.today());
    }

    @Override
    public void decorate(DayViewFacade view)
    {
        // mark the current day with a dot
        view.addSpan(new DotSpan(5, color));
    }
}

/**
 * DoubleDotSpan is an alternative to the DotSpan class,
 * used to mark the days with dots. DoubleDotSpan can add two dots,
 * and can be customized to add more.
 * <p/>
 * Create a span to draw two dots using default radius and colors
 *
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p/>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p/>
 * Create a span to draw two dots using a specified radius and colors
 */

/*
class DoubleDotSpan implements LineBackgroundSpan
{

    public static final float DEFAULT_RADIUS = 5;

    private final float radius;
    private final int color_left;
    private final int color_right;

    */
/**
 * Create a span to draw two dots using default radius and colors
 *
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 *//*

    public DoubleDotSpan()
    {
        this(DEFAULT_RADIUS, 0, 0);
    }

    */
/**
 * Create a span to draw two dots using default radius and specified colors
 *
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 *//*

    public DoubleDotSpan(int colorLeft, int colorRight)
    {
        this(DEFAULT_RADIUS, colorLeft, colorRight);
    }

    */
/**
 * Create a span to draw two dots using a specified radius and colors
 *
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 *//*

    public DoubleDotSpan(float radius, int colorLeft, int colorRight)
    {
        this.radius = radius;
        this.color_left = colorLeft;
        this.color_right = colorRight;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    )
    {
        int oldColor = paint.getColor();
        final float center_x = (left + right) / 2;
        final float center_y = bottom + radius;
        final float shift_x = radius * 2;

        if (color_left != 0)
        {
            paint.setColor(color_left);
        }

        canvas.drawCircle(center_x - shift_x, center_y, radius, paint);

        if (color_right != 0)
        {
            paint.setColor(color_right);
        }

        canvas.drawCircle(center_x + shift_x, center_y, radius, paint);

        paint.setColor(oldColor);
    }
}*/
