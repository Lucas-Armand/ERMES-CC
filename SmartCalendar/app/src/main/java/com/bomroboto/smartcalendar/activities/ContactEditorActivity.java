package com.bomroboto.smartcalendar.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.models.Contact;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactEditorActivity extends AppCompatActivity {

    CircleImageView ivPicture;
    String color;
    EditText etName;
    EditText etPhone;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_editor);

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
    }

    public String getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        return "#" + Integer.toHexString(color);
    }

    Contact getContact()
    {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();

        if (!name.isEmpty())
        {
            if (!phone.isEmpty())
            {
                if (isValidPhone(phone))
                {
                    if (!email.isEmpty())
                    {
                        if (isValidEmail(email))
                        {
                            Contact contact = new Contact();
                            contact.setName(name);
                            contact.setPhone(phone);
                            contact.setEmail(email);
                            contact.setPicture(color);

                            return contact;
                        }
                        else
                        {
                            etEmail.setError("E-mail inválido.");
                        }
                    }
                    else
                    {
                        etEmail.setError("Você não pode deixar este campo em branco.");
                    }
                }
                else
                {
                    etPhone.setError("Telefone inválido.");
                }
            }
            else
            {
                etPhone.setError("Você não pode deixar este campo em branco.");
            }
        }
        else
        {
            etName.setError("Você não pode deixar este campo em branco.");
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
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
            {
                //Toast.makeText(this, "Algo não está certo. Avise ao programador.", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean isValidPhone(String phone)
    {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
