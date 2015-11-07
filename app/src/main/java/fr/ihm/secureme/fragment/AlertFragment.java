package fr.ihm.secureme.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import fr.ihm.secureme.R;
import fr.ihm.secureme.activity.MainActivity;
import fr.ihm.secureme.activity.TriggerActivity;

import com.github.machinarius.preferencefragment.PreferenceFragment;

import java.util.zip.Inflater;

public class AlertFragment extends Fragment implements TitleFragmentInterface{
    private View mFragmentView;
    private ImageButton buttonsim;
    private ImageButton buttoncable;
    private ImageButton buttondist;
    private ImageButton buttonmvt;
    private Switch swsim;
    private Switch swcable;
    private Switch swdist;
    private Switch swmvt;
    private ImageView imsim;
    private ImageView imcable;
    private ImageView imdist;
    private ImageView immvt;



    private LayoutInflater mLayoutInflater;

    public static CharSequence sTitle;

    public AlertFragment() {

    }

    public void onResume(){
        super.onResume();

    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sTitle = getActivity().getString(R.string.trigger_states_title);
        mFragmentView = inflater.inflate(R.layout.fragment_alert, container, false);
        mLayoutInflater = inflater;
        buttonsim = (ImageButton) mFragmentView.findViewById(R.id.configsim);
        swsim = (Switch) mFragmentView.findViewById(R.id.switchsim);
        imsim = (ImageView) mFragmentView.findViewById(R.id.sim_icon);
        buttoncable = (ImageButton) mFragmentView.findViewById(R.id.configcable);
        swcable = (Switch) mFragmentView.findViewById(R.id.switchcable);
        imcable = (ImageView) mFragmentView.findViewById(R.id.cable_icon);
        buttondist = (ImageButton) mFragmentView.findViewById(R.id.configdist);
        swdist = (Switch) mFragmentView.findViewById(R.id.switchdist);
        imdist = (ImageView) mFragmentView.findViewById(R.id.dist_icon);
        buttonmvt = (ImageButton) mFragmentView.findViewById(R.id.configmvt);
        swmvt = (Switch) mFragmentView.findViewById(R.id.switchmvt);
        immvt = (ImageView) mFragmentView.findViewById(R.id.mvt_icon);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(sp.getBoolean("mode_sim",false)){
            swsim.setChecked(true);
            imsim.setImageResource(R.drawable.ic_communication_no_sim);
        }/*
        if(){

        }
        if(){

        }
        if(){

        }*/



        buttonsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simButtonEventHandler();
            }
        });
        buttoncable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cableButtonEventHandler();
            }
        });
        buttondist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distButtonEventHandler();
            }
        });
        buttonmvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvtButtonEventHandler();
            }
        });
        return mFragmentView;
    }

    private void mvtButtonEventHandler() {
        Intent intent = new Intent(getActivity(), TriggerActivity.class);
        intent.putExtra("EXTRA_mode","mvt");
        startActivity(intent);
    }

    private void distButtonEventHandler() {
        Intent intent = new Intent(getActivity(), TriggerActivity.class);
        intent.putExtra("EXTRA_mode","dist");
        startActivity(intent);
    }

    private void cableButtonEventHandler() {
        Intent intent = new Intent(getActivity(), TriggerActivity.class);
        intent.putExtra("EXTRA_mode","cable");
        startActivity(intent);
    }

    private void simButtonEventHandler() {
        Intent intent = new Intent(getActivity(), TriggerActivity.class);
        intent.putExtra("EXTRA_mode","sim");
        startActivity(intent);
    }


    @Override
    public CharSequence getTitle() {
        return sTitle;
    }
}
