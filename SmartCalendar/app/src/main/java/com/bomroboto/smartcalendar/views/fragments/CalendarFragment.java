package com.bomroboto.smartcalendar.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.views.activities.AppointmentInfoActivity;
import com.bomroboto.smartcalendar.models.Appointment;
import com.bomroboto.smartcalendar.models.Business;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    public static String TITLE = "Calendário";

    MaterialCalendarView materialCalendarView;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    List<Business> businesses;
    public ArrayList<Appointment> appointments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
        {
            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }*/

        businesses = SQLite.select().from(Business.class).queryList();
        appointments = new ArrayList<>();
        appointments.addAll(businesses.get(0).getAppointments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        final List<CalendarDay> dates = new ArrayList<>();

        for (Appointment appointment : appointments) {
            dates.add(CalendarDay.from(appointment.getDate().getYear(), appointment.getDate().getMonthOfYear(), appointment.getDate().getDayOfMonth()));
        }

        materialCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return dates.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5, ContextCompat.getColor(getActivity(), R.color.accent)));
            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                ArrayList<Appointment> list = new ArrayList<>();

                for (int i = 0; i < dates.size(); i++) {
                    if (dates.get(i).equals(date)) {
                        list.add(appointments.get(i));
                    }
                }

                AppointmentInfoActivity.start(getActivity(), list);
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calendar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_today) {
            Calendar today = Calendar.getInstance();
            materialCalendarView.setSelectedDate(today.getTime());
            materialCalendarView.setCurrentDate(today.getTime());
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * It's okay to leave MarkTodayDecorator here (and not in a specific package) for now. After all,
     * MarkTodayDecorator will only be used by CalendarFragment.
     */

    private class Decorator implements DayViewDecorator {
        int color;

        Decorator(Activity context) {
            // get the primary color of the style sheet
            color = ContextCompat.getColor(context, R.color.primary);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            // if day is the current day, decorate
            return day.equals(CalendarDay.today());
        }

        @Override
        public void decorate(DayViewFacade view) {
            // mark the current day with a dot
            view.addSpan(new DotSpan(5, color));
        }
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
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
 * @param radius     radius for the dot
 * @param colorLeft  color of the left dot
 * @param colorRight color of the right dot
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
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using a specified radius and colors
 * <p>
 * Create a span to draw two dots using default radius and colors
 * @see #DoubleDotSpan()
 * @see #DEFAULT_RADIUS
 * <p>
 * Create a span to draw two dots using default radius and specified colors
 * @see #DoubleDotSpan(int, int)
 * @see #DEFAULT_RADIUS
 * <p>
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
