package fr.ihm.secureme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ihm.secureme.R;
import com.github.machinarius.preferencefragment.PreferenceFragment;

import java.util.zip.Inflater;

public class AlertFragment extends Fragment implements TitleFragmentInterface{
    private View mFragmentView;
    private LayoutInflater mLayoutInflater;

    public static CharSequence sTitle;

    public AlertFragment() {

    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sTitle = getActivity().getString(R.string.trigger_states_title);
        return inflater.inflate(R.layout.fragment_alert, container, false);
    }



    @Override
    public CharSequence getTitle() {
        return sTitle;
    }
}
