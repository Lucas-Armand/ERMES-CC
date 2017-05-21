package com.bomroboto.smartcalendar.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bomroboto.smartcalendar.R;
import com.bomroboto.smartcalendar.presenters.HomePresenter;
import com.bomroboto.smartcalendar.views.adapters.PagerAdapter;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;

    HomePresenter presenter;

    public static void start(Activity callerActivity) {
        Intent intent = new Intent(callerActivity, HomeActivity.class);
        callerActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        presenter = new HomePresenter();
        presenter.onViewCreated();
    }

    void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
