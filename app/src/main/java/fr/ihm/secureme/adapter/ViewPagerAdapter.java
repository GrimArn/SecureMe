package fr.ihm.secureme.adapter;

/**
 * Created by nonau on 09/10/15.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import fr.ihm.secureme.fragment.AlertFragment;
import fr.ihm.secureme.fragment.ContactFragment;
import fr.ihm.secureme.fragment.SettingsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int Titles[];
    int NumbOfTabs;
    private Context mContext;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(Context c, FragmentManager fm,int mTitles[], int mNumbOfTabsumb) {
        super(fm);
        mContext = c;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 :
                AlertFragment alertFragment = new AlertFragment();
                return alertFragment;
            case 1 :
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;
            case 2 :
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
        }
        return null;

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = mContext.getResources().getDrawable(Titles[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}