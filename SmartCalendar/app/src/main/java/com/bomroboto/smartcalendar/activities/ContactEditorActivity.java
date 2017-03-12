package com.bomroboto.smartcalendar.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.fragments.ContactsFragment;
import com.bomroboto.smartcalendar.models.Contact;

import java.util.Random;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactEditorActivity extends AppCompatActivity
{
    int requestCode;
    Contact contact;

    CircleImageView ivPicture;
    String color;
    EditText etName;
    EditText etPhone;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_editor);

        requestCode = getIntent().getExtras().getInt("requestCode");

        if (getSupportActionBar() != null)
        {
            switch (requestCode)
            {
                case ContactsFragment.ADD_CONTACT_REQUEST_CODE:
                    getSupportActionBar().setTitle("Adicionar contato");
                    break;

                case ContactInfoActivity.EDIT_CONTACT_REQUEST_CODE:
                    getSupportActionBar().setTitle("Editar contato");
                    break;
            }

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        contact = (Contact) getIntent().getSerializableExtra("contact");

        initViews();
    }

    void initViews()
    {
        ivPicture = (CircleImageView) findViewById(R.id.ivPicture);
        color = getRandomColor();
        ivPicture.setColorFilter(Color.parseColor(color));
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);

        if (requestCode == ContactInfoActivity.EDIT_CONTACT_REQUEST_CODE)
        {
            color = contact.getPicture();
            ivPicture.setColorFilter(Color.parseColor(color));
            etName.setText(contact.getName());
            etPhone.setText(contact.getPhone());
            etEmail.setText(contact.getEmail());
        }
    }

    public String getRandomColor()
    {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        return "#" + Integer.toHexString(color);
    }

    Contact getContact()
    {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();

        if (isNameValid(name))
        {
            if (isPhoneValid(phone))
            {
                if (isEmailValid(email))
                {
                    contact.setName(name);
                    contact.setPhone(phone);
                    contact.setEmail(email);
                    contact.setPicture(color);

                    return contact;
                }
            }
        }

        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_done)
        {
            if (getContact() != null)
            {
                getContact().save();

                Intent intent = new Intent();
                intent.putExtra("contact", contact);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            else
            {
                //Toast.makeText(this, "Algo não está certo. Avise ao programador.", Toast.LENGTH_LONG).show();
            }
        }

        if (id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isNameValid(String name)
    {
        if (!name.isEmpty())
        {
            String regx = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
            Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);

            if (pattern.matcher(name).find())
            {
                return true;
            }
            else
            {
                etName.setError("Nome inválido.");
                return false;
            }
        }
        else
        {
            etName.setError("Você não pode deixar este campo em branco.");
            return false;
        }
    }

    public boolean isPhoneValid(String phone)
    {
        if (!phone.isEmpty())
        {
            if (Patterns.PHONE.matcher(phone).matches())
            {
                return true;
            }
            else
            {
                etPhone.setError("Telefone inválido.");
                return false;
            }
        }
        else
        {
            etPhone.setError("Você não pode deixar este campo em branco.");
            return false;
        }
    }

    public boolean isEmailValid(String email)
    {
        if (!email.isEmpty())
        {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                return true;
            }
            else
            {
                etEmail.setError("E-mail inválido.");
                return false;
            }
        }
        else
        {
            etEmail.setError("Você não pode deixar este campo em branco.");
            return false;
        }
    }
}
