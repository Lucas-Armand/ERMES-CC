package com.bomroboto.smartcalendar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bomroboto.smartcalendar.R;

public class ContactsFragment extends Fragment
{
    public static String TITLE = "Contatos";

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        return root;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_contacts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
