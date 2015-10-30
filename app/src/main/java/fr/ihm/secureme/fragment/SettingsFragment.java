package fr.ihm.secureme.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.machinarius.preferencefragment.PreferenceFragment;
import fr.ihm.secureme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements TitleFragmentInterface{


    public static CharSequence sTitle;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.preference);
        sTitle = getActivity().getString(R.string.action_settings);
    }

    @Override
    public CharSequence getTitle() {
        return sTitle;
    }
}
