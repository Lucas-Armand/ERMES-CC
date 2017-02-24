package com.bomroboto.smartcalendar.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Contact;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Andrei Benincasa on 22/02/2017.
 * andrei.benincasa@gmail.com
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView contactPicture;
        public TextView contactName;
        public TextView contactPhone;
        public TextView contactEmail;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView)
        {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            contactPicture = (ImageView) itemView.findViewById(R.id.contact_picture);
            contactName = (TextView) itemView.findViewById(R.id.contact_name);
            contactPhone = (TextView) itemView.findViewById(R.id.contact_phone);
            contactEmail = (TextView) itemView.findViewById(R.id.contact_email);
        }
    }

    // Store a member variable for the contacts
    private List<Contact> contacts;
    // Store the context for easy access
    private Context context;

    // Pass in the contact array into the constructor
    public ContactsAdapter(Context context, List<Contact> contacts)
    {
        this.contacts = contacts;
        this.context = context;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(config);
    }

    // Easy access to the context object in the recyclerview
    private Context getContext()
    {
        return context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position)
    {
        // Get the data model based on position
        Contact contact = contacts.get(position);

        // Set item views based on your views and data model
        ImageView contactPicture = viewHolder.contactPicture;

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(contact.getPicture().getMedium(), contactPicture);

        TextView contactName = viewHolder.contactName;
        contactName.setText(contact.getName().getFirst());

        TextView contactPhone = viewHolder.contactPhone;
        contactPhone.setText(contact.getPhone());

        TextView contactEmail = viewHolder.contactEmail;
        contactEmail.setText(contact.getEmail());
    }

    @Override
    public int getItemCount()
    {
        return contacts.size();
    }
}
