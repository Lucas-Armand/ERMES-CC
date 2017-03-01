package com.bomroboto.smartcalendar.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Contact;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoActivity extends AppCompatActivity {

    CircleImageView ivPicture;
    TextView contactName;
    TextView contactPhone;
    TextView contactEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        initViews();

        Contact contact = (Contact) getIntent().getSerializableExtra("Contact");

        ivPicture.setColorFilter(Color.parseColor(contact.getPicture()));
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactEmail.setText(contact.getEmail());
    }

    void initViews() {
        ivPicture = (CircleImageView) findViewById(R.id.ivPicture);
        contactName = (TextView) findViewById(R.id.contactName);
        contactPhone = (TextView) findViewById(R.id.contactPhone);
        contactEmail = (TextView) findViewById(R.id.contactEmail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_account_edit) {

        }

        return super.onOptionsItemSelected(item);
    }
}
