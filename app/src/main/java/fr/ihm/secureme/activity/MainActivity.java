package fr.ihm.secureme.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import fr.ihm.secureme.R;
import fr.ihm.secureme.adapter.ViewPagerAdapter;
import fr.ihm.secureme.fragment.AlertFragment;
import fr.ihm.secureme.fragment.ContactFragment;
import fr.ihm.secureme.fragment.SettingsFragment;
import fr.ihm.secureme.fragment.TitleFragmentInterface;
import fr.ihm.secureme.google.IconSlidingTabLayout;
import fr.ihm.secureme.google.SlidingTabLayout;
import fr.ihm.secureme.json.ContactJsonWriter;
import fr.ihm.secureme.model.Contact;
import fr.ihm.secureme.model.ContactListSingleton;
import fr.ihm.secureme.tools.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity" ;
    ViewPager pager;
    ViewPagerAdapter adapter;
    IconSlidingTabLayout tabs;
    private int[] imageResId = {
            R.drawable.ic_error_outline_white_24dp,
            R.drawable.ic_group_white_24dp,
            R.drawable.ic_settings_white_24dp
    };
    int Numboftabs =3;
    private List<TitleFragmentInterface> mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListFragment = new ArrayList<TitleFragmentInterface>(Numboftabs);
        mListFragment.add(new AlertFragment());
        mListFragment.add(new ContactFragment());
        mListFragment.add(new SettingsFragment());

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(this,getSupportFragmentManager(),imageResId,Numboftabs);
        ActionBar ab = getSupportActionBar();
        ab.setElevation(0);
        getSupportActionBar().setTitle(getString(R.string.trigger_states_title));
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);


        // Assiging the Sliding Tab Layout View
        tabs = (IconSlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabView(R.layout.custom_tab, 0);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setActivityTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setActivityTitle(int i) {
        getSupportActionBar().setTitle(mListFragment.get(i).getTitle());
    }
}
