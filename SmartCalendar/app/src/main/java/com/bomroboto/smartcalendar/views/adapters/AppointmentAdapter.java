package com.bomroboto.smartcalendar.views.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Appointment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrei Benincasa on 22/02/2017.
 * andrei.benincasa@gmail.com
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        //public CircleImageView appointmentPicture;
        //public TextView appointmentName;

        public TextView tvTime;
        public TextView tvDuration;
        public TextView tvService;
        public TextView tvStatus;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            //appointmentPicture = (CircleImageView) itemView.findViewById(R.id.appointment_picture);
            //appointmentName = (TextView) itemView.findViewById(R.id.appointment_name);

            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            tvService = (TextView) itemView.findViewById(R.id.tvService);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    // Store a member variable for the appointments
    List<Appointment> appointments;
    // Store the context for easy access
    private Context context;

    // Pass in the appointment array into the constructor
    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        this.appointments = appointments;
        this.context = context;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(config);
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View appointmentView = inflater.inflate(R.layout.item_appointment, parent, false);

        // Return a new holder instance
        return new ViewHolder(appointmentView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(AppointmentAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Appointment appointment = appointments.get(position);

        // Set item com.bomroboto.smartcalendar.views based on your com.bomroboto.smartcalendar.views and data model
        //CircleImageView appointmentPicture = viewHolder.appointmentPicture;
        //appointmentPicture.setColorFilter(Color.parseColor(appointment.getPicture()));

        // Setup the appointment picture
        //ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(appointment.getPicture().getMedium(), appointmentPicture);

        //TextView appointmentName = viewHolder.appointmentName;
        //appointmentName.setText(appointment.getName());

        viewHolder.tvTime.setText(appointment.getTime().toString("HH:mm"));
        String duration = appointment.getService().getDuration() + "min";
        viewHolder.tvDuration.setText(duration);
        viewHolder.tvService.setText(appointment.getService().getName());
        viewHolder.tvStatus.setText(appointment.getStatus());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
