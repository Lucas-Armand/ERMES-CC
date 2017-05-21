package com.bomroboto.smartcalendar.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Contact;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoActivity extends AppCompatActivity
{
    public static final int EDIT_CONTACT_REQUEST_CODE = 2;

    CircleImageView ivPicture;
    TextView contactName;
    TextView contactPhone;
    TextView contactEmail;

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Detalhes");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();

        loadInfo(getIntent());
    }

    void initViews()
    {
        ivPicture = (CircleImageView) findViewById(R.id.ivPicture);
        contactName = (TextView) findViewById(R.id.contactName);
        contactPhone = (TextView) findViewById(R.id.contactPhone);
        contactEmail = (TextView) findViewById(R.id.contactEmail);
    }

    void loadInfo(Intent intent)
    {
        contact = (Contact) intent.getSerializableExtra("contact");

        ivPicture.setColorFilter(Color.parseColor(contact.getPicture()));
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactEmail.setText(contact.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_account_edit)
        {
            Intent intent = new Intent(this, ContactEditorActivity.class);
            intent.putExtra("contact", contact);
            intent.putExtra("requestCode", EDIT_CONTACT_REQUEST_CODE);
            startActivityForResult(intent, EDIT_CONTACT_REQUEST_CODE);
        }

        if (id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == EDIT_CONTACT_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                //UPDATE VIEWS
                loadInfo(data);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
