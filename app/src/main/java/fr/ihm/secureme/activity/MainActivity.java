package fr.ihm.secureme.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import fr.ihm.secureme.R;
import fr.ihm.secureme.adapter.ViewPagerAdapter;
import fr.ihm.secureme.google.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {


    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    private int[] imageResId = {
            R.drawable.ic_error_outline_white_24dp,
            R.drawable.ic_wifi_tethering_white_24dp,
            R.drawable.ic_settings_white_24dp
    };
    int Numboftabs =3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(this,getSupportFragmentManager(),imageResId,Numboftabs);
        getSupportActionBar().setElevation(0);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setCustomTabView(R.layout.custom_tab, 0);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }
}
