package com.bomroboto.smartcalendar.views.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Customer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrei Benincasa on 22/02/2017.
 * andrei.benincasa@gmail.com
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
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
        public CircleImageView customerPicture;
        public TextView customerName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            customerPicture = (CircleImageView) itemView.findViewById(R.id.customer_picture);
            customerName = (TextView) itemView.findViewById(R.id.customer_name);

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

    // Store a member variable for the customers
    List<Customer> customers;
    // Store the context for easy access
    private Context context;

    // Pass in the customer array into the constructor
    public CustomerAdapter(Context context, List<Customer> customers) {
        this.customers = customers;
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
    public CustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View customerView = inflater.inflate(R.layout.item_customer, parent, false);

        // Return a new holder instance
        return new ViewHolder(customerView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CustomerAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Customer customer = customers.get(position);

        // Set item com.bomroboto.smartcalendar.views based on your com.bomroboto.smartcalendar.views and data model
        //CircleImageView customerPicture = viewHolder.customerPicture;
        //customerPicture.setColorFilter(Color.parseColor(customer.getPicture()));

        // Setup the customer picture
        //ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(customer.getPicture().getMedium(), customerPicture);

        TextView customerName = viewHolder.customerName;
        customerName.setText(customer.getName());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
