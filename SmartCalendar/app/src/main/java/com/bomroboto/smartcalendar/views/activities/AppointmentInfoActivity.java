package com.bomroboto.smartcalendar.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Appointment;
import com.bomroboto.smartcalendar.models.Customer;
import com.bomroboto.smartcalendar.views.BaseActivity;
import com.bomroboto.smartcalendar.views.adapters.AppointmentAdapter;
import com.bomroboto.smartcalendar.views.adapters.CustomerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class AppointmentInfoActivity extends AppCompatActivity {

    public static final String APPOINTMENT = "appointment";

    RecyclerView rvAppointments;
    AppointmentAdapter adapter;
    TextView tvNoAppointments;

    ArrayList<Appointment> appointments;

    public static void start(Activity callerActivity, ArrayList<Appointment> appointments) {
        Intent intent = new Intent(callerActivity, AppointmentInfoActivity.class);
        intent.putExtra(APPOINTMENT, appointments);
        callerActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);

        appointments = (ArrayList<Appointment>) getIntent().getSerializableExtra(APPOINTMENT);

        initViews();
    }

    void initViews() {
        tvNoAppointments = (TextView) findViewById(R.id.tvNoAppointments);

        // Lookup the recyclerview in activity layout
        rvAppointments = (RecyclerView) findViewById(R.id.rvAppointments);


        // Create adapter passing in the sample user data
        adapter = new AppointmentAdapter(this, appointments);

        rvAppointments.setAdapter(adapter);
        // Set layout manager to position the items
        rvAppointments.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new AppointmentAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View itemView, int position)
            {
                Appointment appointment = appointments.get(position);

                //TODO
                //Intent intent = new Intent(getContext(), CustomerInfoActivity.class);
                //intent.putExtra("customer", customer);
                //startActivity(intent);
            }
        });
        //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //rvAppointments.addItemDecoration(itemDecoration);

        // That's all!

        if (!appointments.isEmpty())
        {
            rvAppointments.setVisibility(View.VISIBLE);
            tvNoAppointments.setVisibility(View.INVISIBLE);
        }
    }
}
