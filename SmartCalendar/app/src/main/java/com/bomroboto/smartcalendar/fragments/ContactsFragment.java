package com.bomroboto.smartcalendar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.adapters.ContactsAdapter;
import com.bomroboto.smartcalendar.interfaces.RandomContactsService;
import com.bomroboto.smartcalendar.models.Contact;
import com.bomroboto.smartcalendar.models.ContactHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsFragment extends Fragment {
    public static String TITLE = "Contatos";

    RecyclerView recyclerView;

    ArrayList<Contact> contacts;
    private ContactsAdapter adapter;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        contacts = new ArrayList<>();

        // Create adapter passing in the sample user data
        adapter = new ContactsAdapter(getContext(), contacts);
        // Attach the adapter to the recyclerview to populate items
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Lookup the recyclerview in activity layout
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(itemDecoration);

        // That's all!

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contacts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_account_plus)
        {

        }

        return super.onOptionsItemSelected(item);
    }
}
