package com.bomroboto.smartcalendar.views.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Contact;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrei Benincasa on 22/02/2017.
 * andrei.benincasa@gmail.com
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
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
        public CircleImageView contactPicture;
        public TextView contactName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            contactPicture = (CircleImageView) itemView.findViewById(R.id.contact_picture);
            contactName = (TextView) itemView.findViewById(R.id.contact_name);

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

    // Store a member variable for the contacts
    List<Contact> contacts;
    // Store the context for easy access
    private Context context;

    // Pass in the contact array into the constructor
    public ContactsAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
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
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Contact contact = contacts.get(position);

        // Set item com.bomroboto.smartcalendar.views based on your com.bomroboto.smartcalendar.views and data model
        CircleImageView contactPicture = viewHolder.contactPicture;
        contactPicture.setColorFilter(Color.parseColor(contact.getPicture()));

        // Setup the contact picture
        //ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(contact.getPicture().getMedium(), contactPicture);

        TextView contactName = viewHolder.contactName;
        contactName.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
