package fr.ihm.secureme.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.github.machinarius.preferencefragment.PreferenceFragment;
import fr.ihm.secureme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements TitleFragmentInterface, SharedPreferences.OnSharedPreferenceChangeListener {


    public static CharSequence sTitle;
    private SharedPreferences sharedPreferences;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.preference);

        sTitle = getActivity().getString(R.string.action_settings);
        sharedPreferences = getPreferenceManager().getSharedPreferences();

        setPreferenceSummary();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public CharSequence getTitle() {
        return sTitle;
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(isAdded()) {
            setPreferenceSummary();
        }
    }

    private void setPreferenceSummary() {
        if(isAdded()) {
            EditTextPreference num = (EditTextPreference) findPreference("pref_key_emer_num");
            num.setSummary(sharedPreferences.getString("pref_key_emer_num", getResources().getString(R.string.pref_summary_emer_num)));

            EditTextPreference text = (EditTextPreference) findPreference("pref_key_emer_text");
            text.setSummary(sharedPreferences.getString("pref_key_emer_text", getResources().getString(R.string.pref_summary_emer_text)));
        }
    }
}
