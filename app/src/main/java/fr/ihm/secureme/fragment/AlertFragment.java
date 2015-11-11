package fr.ihm.secureme.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import fr.ihm.secureme.R;
import fr.ihm.secureme.activity.TriggerActivity;
import fr.ihm.secureme.services.MotionDetector;

import static com.google.android.gms.cast.CastRemoteDisplayLocalService.startService;

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
        updateView();
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final SharedPreferences.Editor editor = preferences.edit();
        final TelephonyManager telephoneMgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE); //used in the switch listener
        sTitle = getActivity().getString(R.string.trigger_states_title);
        mFragmentView = inflater.inflate(R.layout.fragment_alert, container, false);
        mLayoutInflater = inflater;
        buttonsim = (ImageButton) mFragmentView.findViewById(R.id.configsim);
        swsim = (Switch) mFragmentView.findViewById(R.id.switchsim);
        if(telephoneMgr.getSimState()==1){
            swsim.setEnabled(false);
        }
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
        updateView();

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

        swsim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//activation
                    editor.putBoolean("mode_sim", true);
                    String simSerialNumber = telephoneMgr.getSimSerialNumber();
                    editor.putString("SIM_ID", simSerialNumber);
                    editor.commit();
                } else { //désactivation
                    editor.putBoolean("mode_sim", false);
                    editor.commit();
                }
                updateView();
            }
        });
        swcable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//activation
                    editor.putBoolean("mode_cable", true);
                    editor.commit();
                } else { //désactivation
                    editor.putBoolean("mode_cable", false);
                    editor.commit();
                }
                updateView();
            }
        });
        swdist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//activation
                    editor.putBoolean("mode_dist", true);
                    editor.commit();
                } else { //désactivation
                    editor.putBoolean("mode_dist", false);
                    editor.commit();
                }
                updateView();
            }
        });
        swmvt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//activation
                    editor.putBoolean("mode_mvt", true);
                    editor.commit();
                } else { //désactivation
                    editor.putBoolean("mode_mvt", false);
                    editor.commit();
                }
                updateView();
            }
        });
        return mFragmentView;
    }

    private void updateView() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(sp.getBoolean("mode_sim",false)){
            swsim.setChecked(true);
            imsim.setImageResource(R.drawable.ic_communication_no_sim);
        } else {
            swsim.setChecked(false);
            imsim.setImageResource(R.drawable.ic_no_sim_white_48dp);
        }
        if(sp.getBoolean("mode_cable",false)){
            swcable.setChecked(true);
            imcable.setImageResource(R.drawable.ic_cable_on);
        }else{
            swcable.setChecked(false);
            imcable.setImageResource(R.drawable.ic_cable_off);
        }
        if(sp.getBoolean("mode_dist",false)){
            swdist.setChecked(true);
            imdist.setImageResource(R.drawable.ic_communication_location_on);
        } else {
            swdist.setChecked(false);
            imdist.setImageResource(R.drawable.ic_location_off_white_48dp);
        }
        if(sp.getBoolean("mode_mvt",false)){
            swmvt.setChecked(true);
            immvt.setImageResource(R.drawable.ic_device_screen_rotation);
        } else {
            swmvt.setChecked(false);
            immvt.setImageResource(R.drawable.ic_screen_rotation_white_48dp);
        }
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
