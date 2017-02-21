package com.bomroboto.smartcalendar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        return root;
    }

}
