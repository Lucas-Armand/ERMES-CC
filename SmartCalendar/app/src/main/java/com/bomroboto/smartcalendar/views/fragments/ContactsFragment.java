package com.bomroboto.smartcalendar.views.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.views.activities.ContactEditorActivity;
import com.bomroboto.smartcalendar.views.activities.ContactInfoActivity;
import com.bomroboto.smartcalendar.views.adapters.ContactsAdapter;
import com.bomroboto.smartcalendar.models.Business;
import com.bomroboto.smartcalendar.models.Contact;
import com.bomroboto.smartcalendar.models.Customer;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment
{
    public static String TITLE = "Contatos";
    public static final int ADD_CONTACT_REQUEST_CODE = 1;

    RecyclerView rvContacts;
    TextView tvNoContacts;

    List<Business> businesses;
    ArrayList<Customer> customers;
    ArrayList<Contact> contacts;
    private ContactsAdapter adapter;

    public ContactsFragment()
    {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance()
    {
        return new ContactsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RandomContactsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RandomContactsService service = retrofit.create(RandomContactsService.class);

        Call<ContactHolder> getContacts = service.getContacts(10);

        Log.e("SMARTCALENDAR", getContacts.request().url().toString());

        getContacts.enqueue(new Callback<ContactHolder>()
        {
            @Override
            public void onResponse(Call<ContactHolder> call, Response<ContactHolder> response)
            {
                Log.e("SMARTCALENDAR", response.body() + "");
                contacts.addAll(response.body().getContacts());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ContactHolder> call, Throwable t)
            {
                //Log.e("SMARTCALENDAR", t.getMessage());
                t.printStackTrace();
            }
        });*/

        businesses = SQLite.select().from(Business.class).queryList();

        customers.addAll(businesses.get(0).getCustomers());



        contacts = new ArrayList<>();

        contacts.addAll(SQLite.select()
                .from(Contact.class)
                .queryList());

        // Create adapter passing in the sample user data
        adapter = new ContactsAdapter(getContext(), contacts);
        // Attach the adapter to the recyclerview to populate items
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);

        tvNoContacts = (TextView) root.findViewById(R.id.tvNoContacts);

        // Lookup the recyclerview in activity layout
        rvContacts = (RecyclerView) root.findViewById(R.id.rvContacts);

        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View itemView, int position)
            {
                Contact contact = contacts.get(position);

                Intent intent = new Intent(getContext(), ContactInfoActivity.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
            }
        });
        //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //rvContacts.addItemDecoration(itemDecoration);

        // That's all!

        if (!contacts.isEmpty())
        {
            rvContacts.setVisibility(View.VISIBLE);
            tvNoContacts.setVisibility(View.INVISIBLE);
        }

        return root;
    }

    void updateList()
    {
        contacts.clear();

        contacts.addAll(SQLite.select()
                .from(Contact.class)
                .queryList());

        adapter.notifyDataSetChanged();

        if (!contacts.isEmpty())
        {
            rvContacts.setVisibility(View.VISIBLE);
            tvNoContacts.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_contacts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume()
    {
        updateList();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_account_plus)
        {
                Intent intent = new Intent(getContext(), ContactEditorActivity.class);
                intent.putExtra("requestCode", ADD_CONTACT_REQUEST_CODE);
                startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ADD_CONTACT_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                contacts.clear();

                contacts.addAll(SQLite.select()
                        .from(Contact.class)
                        .queryList());

                adapter.notifyDataSetChanged();

                if (!contacts.isEmpty())
                {
                    rvContacts.setVisibility(View.VISIBLE);
                    tvNoContacts.setVisibility(View.INVISIBLE);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
