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
import com.bomroboto.smartcalendar.models.Business;
import com.bomroboto.smartcalendar.models.Customer;
import com.bomroboto.smartcalendar.presenters.CustomersPresenter;
import com.bomroboto.smartcalendar.views.adapters.CustomerAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class CustomersFragment extends Fragment
{
    public static String TITLE = "Clientes";
    public static final int ADD_CUSTOMER_REQUEST_CODE = 1;

    RecyclerView rvCustomers;
    CustomerAdapter adapter;
    TextView tvNoCustomers;

    CustomersPresenter presenter;

    List<Business> businesses;
    ArrayList<Customer> customers;

    public CustomersFragment()
    {
        // Required empty public constructor
    }

    public static CustomersFragment newInstance()
    {
        return new CustomersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        customers = new ArrayList<>();
        businesses = SQLite.select().from(Business.class).queryList();
        customers.addAll(businesses.get(0).getCustomers());

        // Create adapter passing in the sample user data
        adapter = new CustomerAdapter(getContext(), customers);
        // Attach the adapter to the recyclerview to populate items
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_customers, container, false);

        tvNoCustomers = (TextView) root.findViewById(R.id.tvNoCustomers);

        // Lookup the recyclerview in activity layout
        rvCustomers = (RecyclerView) root.findViewById(R.id.rvCustomers);

        rvCustomers.setAdapter(adapter);
        // Set layout manager to position the items
        rvCustomers.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View itemView, int position)
            {
                Customer customer = customers.get(position);

                //TODO
                //Intent intent = new Intent(getContext(), CustomerInfoActivity.class);
                //intent.putExtra("customer", customer);
                //startActivity(intent);
            }
        });
        //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //rvCustomers.addItemDecoration(itemDecoration);

        // That's all!

        if (!customers.isEmpty())
        {
            rvCustomers.setVisibility(View.VISIBLE);
            tvNoCustomers.setVisibility(View.INVISIBLE);
        }

        return root;
    }

    void updateList()
    {
        customers.clear();

        businesses = SQLite.select().from(Business.class).queryList();
        customers.addAll(businesses.get(0).getCustomers());

        adapter.notifyDataSetChanged();

        if (!customers.isEmpty())
        {
            rvCustomers.setVisibility(View.VISIBLE);
            tvNoCustomers.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_customers, menu);
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
                // TODO
                //Intent intent = new Intent(getContext(), CustomerEditorActivity.class);
                //intent.putExtra("requestCode", ADD_CONTACT_REQUEST_CODE);
                //startActivityForResult(intent, ADD_CONTACT_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ADD_CUSTOMER_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                customers.clear();

                customers.addAll(SQLite.select()
                        .from(Customer.class)
                        .queryList());

                adapter.notifyDataSetChanged();

                if (!customers.isEmpty())
                {
                    rvCustomers.setVisibility(View.VISIBLE);
                    tvNoCustomers.setVisibility(View.INVISIBLE);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
