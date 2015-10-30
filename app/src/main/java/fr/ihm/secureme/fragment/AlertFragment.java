package fr.ihm.secureme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ihm.secureme.R;

public class AlertFragment extends Fragment implements TitleFragmentInterface{


    public static CharSequence sTitle;

    public AlertFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_alert, container, false);
        sTitle = getActivity().getString(R.string.trigger_states_title);
        return v;
    }

    @Override
    public CharSequence getTitle() {
        return sTitle;
    }
}
