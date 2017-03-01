package com.bomroboto.smartcalendar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Contact;

import java.io.Serializable;

public class ContactInfoActivity extends AppCompatActivity{

    TextView contactName;
    TextView contactPhone;
    TextView contactEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        contactName = (TextView) findViewById(R.id.contactName);
        contactPhone = (TextView) findViewById(R.id.contactPhone);
        contactEmail = (TextView) findViewById(R.id.contactEmail);

        Contact contact = (Contact) getIntent().getSerializableExtra("Contact");

        contactName.setText(contact.getFirstName() + " " + contact.getLastName());
        contactPhone.setText(contact.getPhone());
        contactEmail.setText(contact.getEmail());
    }
}
